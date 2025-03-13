package cc.dreamcode.missions.mission.serializer;

import cc.dreamcode.missions.mission.Mission;
import cc.dreamcode.missions.mission.MissionIcon;
import cc.dreamcode.missions.mission.MissionType;
import cc.dreamcode.missions.mission.filter.FilterMode;
import com.cryptomorin.xseries.XMaterial;
import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import lombok.NonNull;

import java.time.Duration;

public class MissionSerializer implements ObjectSerializer<Mission> {

    @Override
    public boolean supports(@NonNull Class<? super Mission> type) {
        return Mission.class.isAssignableFrom(type);
    }

    @Override
    public void serialize(@NonNull Mission object, @NonNull SerializationData data, @NonNull GenericsDeclaration generics) {
        data.add("id", object.getId(), Integer.class);
        data.add("name", object.getName(), String.class);
        data.add("type", object.getType(), MissionType.class);
        data.add("goal-amount", object.getGoalAmount(), Integer.class);
        data.add("goal-command", object.getGoalCommand(), String.class);
        data.add("reset-time", object.getResetTime().toString(), String.class);
        data.add("filter-mode", object.getFilterMode(), FilterMode.class);
        data.add("filter-materials", object.getFilterMaterials());
        data.add("icon", object.getIcon(), MissionIcon.class);
    }

    @Override
    public Mission deserialize(@NonNull DeserializationData data, @NonNull GenericsDeclaration generics) {
        return new Mission(
                data.get("id", Integer.class),
                data.get("name", String.class),
                data.get("type", MissionType.class),
                data.get("goal-amount", Integer.class),
                data.get("goal-command", String.class),
                Duration.parse(data.get("reset-time", String.class)),
                data.get("filter-mode", FilterMode.class),
                data.getAsList("filter-materials", XMaterial.class),
                data.get("icon", MissionIcon.class)
        );
    }
}