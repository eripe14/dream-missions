package cc.dreamcode.missions.mission;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
@Getter
public class MissionIcon {

    private final ItemStack icon;
    private final int slot;

}