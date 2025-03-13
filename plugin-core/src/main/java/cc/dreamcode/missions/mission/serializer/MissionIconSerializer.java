package cc.dreamcode.missions.mission.serializer;

import cc.dreamcode.missions.mission.MissionIcon;
import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import lombok.NonNull;
import org.bukkit.inventory.ItemStack;

public class MissionIconSerializer implements ObjectSerializer<MissionIcon> {

    @Override
    public boolean supports(@NonNull Class<? super MissionIcon> type) {
        return MissionIcon.class.isAssignableFrom(type);
    }

    @Override
    public void serialize(@NonNull MissionIcon object, @NonNull SerializationData data, @NonNull GenericsDeclaration generics) {
        data.add("icon", object.getIcon(), ItemStack.class);
        data.add("slot", object.getSlot(), Integer.class);
    }

    @Override
    public MissionIcon deserialize(@NonNull DeserializationData data, @NonNull GenericsDeclaration generics) {
        return new MissionIcon(
                data.get("icon", ItemStack.class),
                data.get("slot", Integer.class)
        );
    }
}