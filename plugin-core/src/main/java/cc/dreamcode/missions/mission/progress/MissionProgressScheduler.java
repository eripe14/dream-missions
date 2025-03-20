package cc.dreamcode.missions.mission.progress;

import cc.dreamcode.missions.mission.MissionService;
import cc.dreamcode.platform.bukkit.component.scheduler.Scheduler;
import eu.okaeri.injector.annotation.Inject;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Scheduler(delay = 20, interval = 20)
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class MissionProgressScheduler implements Runnable {

    private final MissionService missionService;

    @Override
    public void run() {
        if (this.missionService.getUpdateQueue().isEmpty()) {
            return;
        }

        List<MissionProgress> batchUpdates = new ArrayList<>();

        int count = 0;
        int batchLimit = 100;

        while (!this.missionService.getUpdateQueue().isEmpty() && count < batchLimit) {
            MissionProgress progress = this.missionService.getUpdateQueue().poll();
            batchUpdates.add(progress);
            count++;
        }

        if (!batchUpdates.isEmpty()) {
            this.missionService.saveBatchProgressAsync(batchUpdates);
        }
    }

}