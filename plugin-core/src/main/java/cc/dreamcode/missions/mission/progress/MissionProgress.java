package cc.dreamcode.missions.mission.progress;

import eu.okaeri.configs.annotation.CustomKey;
import eu.okaeri.persistence.document.Document;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.Duration;
import java.time.Instant;

@Data
@EqualsAndHashCode(callSuper = false)
public class MissionProgress extends Document {

    @CustomKey("mission-id")
    private int missionId;

    @CustomKey("current-amount")
    private double currentAmount;

    @CustomKey("finished")
    private boolean finished;

    @CustomKey("time-to-reset")
    private Duration timeToReset;

    @CustomKey("reset-date")
    private Instant resetDate;

}