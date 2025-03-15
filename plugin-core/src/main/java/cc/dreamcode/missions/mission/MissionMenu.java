package cc.dreamcode.missions.mission;

import cc.dreamcode.menu.adventure.BukkitMenuBuilder;
import cc.dreamcode.menu.adventure.base.BukkitMenu;
import cc.dreamcode.menu.adventure.setup.BukkitMenuPlayerSetup;
import cc.dreamcode.missions.config.PluginConfig;
import cc.dreamcode.missions.mission.progress.MissionProgress;
import cc.dreamcode.utilities.TimeUtil;
import cc.dreamcode.utilities.builder.MapBuilder;
import cc.dreamcode.utilities.bukkit.builder.ItemBuilder;
import eu.okaeri.injector.annotation.Inject;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.HumanEntity;

@RequiredArgsConstructor(onConstructor_ = @Inject)
public class MissionMenu implements BukkitMenuPlayerSetup {

    private final PluginConfig pluginConfig;
    private final MissionService missionService;

    @Override
    public BukkitMenu build(@NonNull HumanEntity humanEntity) {
        MissionConfig missionConfig = this.pluginConfig.missionConfig;

        BukkitMenuBuilder menuBuilder = missionConfig.missionMenuBuilder;
        BukkitMenu bukkitMenu = menuBuilder.buildEmpty();
        bukkitMenu.setDisposeWhenClose(true);

        menuBuilder.getItems().forEach((slot, item) -> {
            if (slot == missionConfig.missionMenuCloseSlot) {
                bukkitMenu.setItem(slot, ItemBuilder.of(item).fixColors().toItemStack(), event -> {
                    event.getWhoClicked().closeInventory();
                });
            }

            bukkitMenu.setItem(slot, ItemBuilder.of(item).fixColors().toItemStack());
        });

        missionConfig.missions.values()
                .forEach(mission -> {
                    int missionId = mission.getId();
                    double progressPercentage = this.missionService.getProgressPercentage(missionId);

                    MissionProgress missionProgress = this.missionService.getMissionProgress(missionId);

                    bukkitMenu.setItem(
                            mission.getIcon().getSlot(),
                            ItemBuilder.of(mission.getIcon().getIcon())
                                    .fixColors(new MapBuilder<String, Object>()
                                            .put("name", mission.getName())
                                            .put("percentage", progressPercentage)
                                            .put("status", progressPercentage >= 100.0
                                                            ? missionConfig.missionStatusFinished
                                                            : missionConfig.missionStatusInProgress
                                            )
                                            .put("time", TimeUtil.format(missionProgress.getTimeToReset()))
                                            .put("complete", missionProgress.getCurrentAmount())
                                            .put("remaining", mission.getGoalAmount() - missionProgress.getCurrentAmount())
                                            .build()
                                    )
                                    .toItemStack()
                    );
                });

        return bukkitMenu;
    }
}