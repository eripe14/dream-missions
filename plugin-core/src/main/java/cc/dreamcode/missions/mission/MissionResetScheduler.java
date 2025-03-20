package cc.dreamcode.missions.mission;

import cc.dreamcode.missions.mission.progress.MissionProgress;
import cc.dreamcode.platform.bukkit.component.scheduler.Scheduler;
import cc.dreamcode.utilities.MathUtil;
import eu.okaeri.injector.annotation.Inject;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;

import java.time.Duration;
import java.time.Instant;

@Scheduler(delay = 20, interval = 20)
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class MissionResetScheduler implements Runnable {

    private final MissionService missionService;

    @Override
    public void run() {
        for (MissionProgress missionProgress : this.missionService.getMissionProgressCache().values()) {
            if (Instant.now().isBefore(missionProgress.getResetDate())) {
                missionProgress.setTimeToReset(Duration.between(Instant.now(), missionProgress.getResetDate()));
                continue;
            }

            Mission mission = this.missionService.getMissions().get(missionProgress.getMissionId());
            if (mission == null) {
                continue;
            }

            missionProgress.setResetDate(Instant.now().plus(mission.getResetTime()));
            missionProgress.setTimeToReset(Duration.between(Instant.now(), missionProgress.getResetDate()));

            this.missionService.resetMission(missionProgress.getMissionId());
        }
    }

}