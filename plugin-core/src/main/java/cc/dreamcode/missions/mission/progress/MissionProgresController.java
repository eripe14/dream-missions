package cc.dreamcode.missions.mission.progress;

import cc.dreamcode.missions.config.MessageConfig;
import cc.dreamcode.missions.config.PluginConfig;
import cc.dreamcode.missions.mission.*;
import eu.okaeri.injector.annotation.Inject;
import eu.okaeri.tasker.core.Tasker;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

@RequiredArgsConstructor(onConstructor_ = @Inject)
public class MissionProgresController implements Listener {

    private final MissionService missionService;
    private final MessageConfig messageConfig;
    private final PluginConfig pluginConfig;

    @EventHandler
    void onMissionFinish(MissionFinishEvent event) {
        MissionConfig missionConfig = this.pluginConfig.missionConfig;
        Mission mission = event.getMission();

        String goalCommand = mission.getGoalCommand();

        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            goalCommand = StringUtils.replace(goalCommand, "{player}", onlinePlayer.getName());
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), goalCommand);
        }

        if (!missionConfig.broadcastMissionFinish) {
            return;
        }

        this.messageConfig.missionFinish
                .with("mission-name", mission.getName())
                .sendAll();
    }

    @EventHandler
    void onBlockBrake(BlockBreakEvent event) {
        this.missionService.updateMissionsProgress(event.getBlock().getType(), MissionType.MINED_BLOCKS, 1);
    }

    @EventHandler
    void onBlockPlace(BlockPlaceEvent event) {
        this.missionService.updateMissionsProgress(event.getBlock().getType(), MissionType.PLACED_BLOCKS, 1);
    }

    @EventHandler
    void onPlayerChat(AsyncPlayerChatEvent event) {
        this.missionService.updateMissionsProgress(MissionType.WRITTEN_MESSAGE, 1);
    }

    @EventHandler
    void onPlayerConsume(PlayerItemConsumeEvent event) {
        this.missionService.updateMissionsProgress(event.getItem().getType(), MissionType.EATEN_ITEMS, 1);
    }

    @EventHandler
    void onPlayerMove(PlayerMoveEvent event) {
        Location from = event.getFrom();
        Location to = event.getTo();

        if (from.getX() == to.getX() && from.getY() == to.getY() && from.getZ() == to.getZ()) {
            return;
        }

        if (from.getX() != to.getX() || from.getZ() != to.getZ() || from.getY() != to.getY()) {
            this.missionService.updateMissionsProgress(MissionType.RUNNING_DISTANCE, from.distance(to));
        }
    }

    @EventHandler
    void onEntityDeath(EntityDeathEvent event) {
        Player killer = event.getEntity().getKiller();

        if (killer != null) {
            this.missionService.updateMissionsProgress(MissionType.KILLED_ENTITIES, 1);
        }
    }

    @EventHandler
    void onCaughtItem(PlayerFishEvent event) {
        PlayerFishEvent.State state = event.getState();

        if (state != PlayerFishEvent.State.CAUGHT_ENTITY && state != PlayerFishEvent.State.CAUGHT_FISH) {
            return;
        }

        Entity caught = event.getCaught();
        if (!(caught instanceof Item)) {
            return;
        }

        Item item = (Item) caught;
        ItemStack itemStack = item.getItemStack();
        Material type = itemStack.getType();

        this.missionService.updateMissionsProgress(type, MissionType.CAUGHT_ITEMS, 1);
    }

}