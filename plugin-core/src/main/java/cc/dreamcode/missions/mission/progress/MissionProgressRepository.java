package cc.dreamcode.missions.mission.progress;

import cc.dreamcode.utilities.MathUtil;
import eu.okaeri.persistence.repository.DocumentRepository;
import eu.okaeri.persistence.repository.annotation.DocumentCollection;

import java.time.Duration;
import java.time.Instant;

@DocumentCollection(path = "missions_progress", keyLength = 36)
public interface MissionProgressRepository extends DocumentRepository<Integer, MissionProgress> {

    default MissionProgress getOrCreate(int id, Duration resetTime) {
        MissionProgress missionProgress = this.findOrCreateByPath(id);
        missionProgress.setResetDate(Instant.now().plus(resetTime));
        missionProgress.setTimeToReset(Duration.between(Instant.now(), missionProgress.getResetDate()));

        return missionProgress;
    }

}