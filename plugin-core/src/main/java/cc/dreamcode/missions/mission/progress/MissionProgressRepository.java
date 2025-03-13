package cc.dreamcode.missions.mission.progress;

import eu.okaeri.persistence.repository.DocumentRepository;
import eu.okaeri.persistence.repository.annotation.DocumentCollection;

@DocumentCollection(path = "missions_progress", keyLength = 36)
public interface MissionProgressRepository extends DocumentRepository<Integer, MissionProgress> {

    default MissionProgress getOrCreate(int id) {
        return this.findOrCreateByPath(id);
    }

}