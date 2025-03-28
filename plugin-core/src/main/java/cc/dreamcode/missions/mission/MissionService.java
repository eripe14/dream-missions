package cc.dreamcode.missions.mission;

import cc.dreamcode.missions.MissionsPlugin;
import cc.dreamcode.missions.config.PluginConfig;
import cc.dreamcode.missions.mission.progress.MissionProgress;
import cc.dreamcode.missions.mission.progress.MissionProgressRepository;
import cc.dreamcode.utilities.DateUtil;
import cc.dreamcode.utilities.MathUtil;
import cc.dreamcode.utilities.TimeUtil;
import eu.okaeri.injector.annotation.Inject;
import eu.okaeri.injector.annotation.PostConstruct;
import eu.okaeri.tasker.core.Tasker;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Material;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

@RequiredArgsConstructor(onConstructor_ = @Inject)
public class MissionService {

    private final PluginConfig config;
    private final MissionProgressRepository missionProgressRepository;
    private final Tasker tasker;

    private final @Getter Map<Integer, Mission> missions = new ConcurrentHashMap<>();
    private final @Getter Map<Integer, MissionProgress> missionProgressCache = new ConcurrentHashMap<>();

    private final @Getter Queue<MissionProgress> updateQueue = new ConcurrentLinkedQueue<>();

    @PostConstruct
    private void init() {
        this.missions.putAll(this.config.missionConfig.missions);
        this.loadAllMissionProgress();
    }

    public MissionProgress getMissionProgress(int id) {
        Mission mission = this.missions.get(id);
        MissionProgress progress = this.missionProgressCache.get(id);

        if (progress == null) {
            this.tasker.newSharedChain("dbops-" + id)
                    .supplyAsync(() -> this.missionProgressRepository.getOrCreate(id, mission.getResetTime()))
                    .acceptAsync(loadedProgress -> {
                        loadedProgress.setResetDate(Instant.now().plus(mission.getResetTime()));

                        this.missionProgressCache.put(id, loadedProgress);
                    })
                    .execute();

            progress = new MissionProgress();
            progress.setMissionId(id);
            progress.setCurrentAmount(0);
            progress.setResetDate(Instant.now().plus(mission.getResetTime()));
            progress.setTimeToReset(Duration.between(Instant.now(), progress.getResetDate()));
            this.missionProgressCache.put(id, progress);
        }

        return progress;
    }

    public void updateMissionsProgress(MissionType missionType, double additionalAmount) {
        for (Mission mission : this.missions.values()) {
            if (mission.getType() != missionType) {
                continue;
            }

            this.updateProgress(mission.getId(), additionalAmount);
        }
    }

    public void updateMissionsProgress(Material material, MissionType missionType, double additionalAmount) {
        for (Mission mission : this.missions.values()) {
            if (mission.getType() != missionType) {
                continue;
            }

            if (!mission.isCorrect(material)) {
                continue;
            }

            this.updateProgress(mission.getId(), additionalAmount);
        }
    }

    private void updateProgress(int missionId, double additionalAmount) {
        MissionProgress progress = this.getMissionProgress(missionId);
        Mission mission = this.missions.get(missionId);

        if (mission == null) {
            return;
        }

        double newAmount = progress.getCurrentAmount() + additionalAmount;
        progress.setTimeToReset(Duration.between(Instant.now(), progress.getResetDate()));

        if (newAmount >= mission.getGoalAmount() && !progress.isFinished()) {
            newAmount = mission.getGoalAmount();

            Bukkit.getScheduler().runTask(MissionsPlugin.getInstance(), () -> {
                Bukkit.getPluginManager().callEvent(new MissionFinishEvent(mission));
            });

            progress.setCurrentAmount(newAmount);
            progress.setFinished(true);
            return;
        }

        progress.setCurrentAmount(newAmount);
        progress.setMissionId(missionId);

        this.missionProgressCache.put(missionId, progress);
        this.updateQueue.add(progress);
    }

    public MissionProgress resetMission(int missionId) {
        MissionProgress progress = this.getMissionProgress(missionId);
        progress.setCurrentAmount(0);
        progress.setFinished(false);

        this.missionProgressCache.put(missionId, progress);
        this.updateQueue.add(progress);
        return progress;
    }

    public double getProgressPercentage(int missionId) {
        MissionProgress progress = this.getMissionProgress(missionId);
        Mission mission = this.missions.get(missionId);

        if (mission == null) {
            return 0.0;
        }

        if (progress.isFinished()) {
            return 100.0;
        }

        return progress.getCurrentAmount() / mission.getGoalAmount() * 100.0;
    }

    public void saveBatchProgressAsync(List<MissionProgress> progressList) {
        this.tasker.newChain()
                .async(() -> progressList.forEach(this.missionProgressRepository::save))
                .execute();
    }

    public void loadAllMissionProgress() {
        this.tasker.newChain()
                .async(() -> {
                    this.missions.values().forEach(mission -> {
                        int missionId = mission.getId();
                        MissionProgress progress = this.missionProgressRepository.getOrCreate(missionId, mission.getResetTime());

                        this.missionProgressCache.put(missionId, progress);
                    });
                })
                .execute();
    }

}