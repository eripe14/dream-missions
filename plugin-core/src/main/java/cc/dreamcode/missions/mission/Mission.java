package cc.dreamcode.missions.mission;

import cc.dreamcode.missions.mission.filter.FilterMode;
import com.cryptomorin.xseries.XMaterial;
import lombok.Getter;
import org.bukkit.Material;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Getter
public class Mission {

    private final int id;
    private final String name;
    private final MissionType type;
    private final int goalAmount;
    private final String goalCommand;
    private final Duration resetTime;
    private final FilterMode filterMode;
    private final List<XMaterial> filterMaterials;
    private final MissionIcon icon;

    public Mission(
            int id,
            String name,
            MissionType type,
            int goalAmount,
            String goalCommand,
            Duration resetTime,
            FilterMode filterMode,
            MissionIcon icon
    ) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.goalAmount = goalAmount;
        this.goalCommand = goalCommand;
        this.resetTime = resetTime;
        this.filterMode = filterMode;
        this.icon = icon;
        this.filterMaterials = new ArrayList<>();
    }

    public Mission(
            int id,
            String name,
            MissionType type,
            int goalAmount,
            String goalCommand,
            Duration resetTime,
            FilterMode filterMode,
            List<XMaterial> filterMaterials,
            MissionIcon icon
    ) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.goalAmount = goalAmount;
        this.goalCommand = goalCommand;
        this.resetTime = resetTime;
        this.filterMode = filterMode;
        this.filterMaterials = filterMaterials;
        this.icon = icon;
    }

    public boolean isCorrect(Material material) {
        XMaterial xMaterial = XMaterial.matchXMaterial(material);

        switch (this.filterMode) {
            case ALL:
                return true;
            case SPECIFIC:
                return this.filterMaterials.contains(xMaterial);
            case EXCLUDE:
                return !this.filterMaterials.contains(xMaterial);
            default:
                return false;
        }
    }
}