package cc.dreamcode.missions.command;

import cc.dreamcode.command.CommandBase;
import cc.dreamcode.command.annotation.*;
import cc.dreamcode.missions.config.MessageConfig;
import cc.dreamcode.missions.config.PluginConfig;
import cc.dreamcode.missions.mission.Mission;
import cc.dreamcode.missions.mission.MissionMenu;
import cc.dreamcode.missions.mission.MissionService;
import cc.dreamcode.missions.mission.progress.MissionProgress;
import cc.dreamcode.notice.bukkit.BukkitNotice;
import cc.dreamcode.utilities.TimeUtil;
import eu.okaeri.configs.exception.OkaeriException;
import eu.okaeri.injector.annotation.Inject;
import eu.okaeri.tasker.core.Tasker;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;

import java.time.Duration;
import java.time.Instant;

@Command(name = "missions")
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class MissionCommand implements CommandBase {

    private final Tasker tasker;
    private final MissionMenu menu;
    private final MissionService missionService;
    private final MessageConfig messageConfig;
    private final PluginConfig pluginConfig;

    @Permission("dream-missions.menu")
    @Executor(description = "Otwiera menu.")
    void menu(Player player) {
        this.tasker.newSharedChain("dbops:" + player.getUniqueId())
                .supplyAsync(() -> this.menu.build(player))
                .acceptSync(bukkitMenu -> bukkitMenu.open(player))
                .execute();
    }

    @Permission("dream-missions.reset")
    @Executor(path = "reset", description = "Resetuje postęp misji.")
    void reset(Player player, @Arg(value = "mission-id") int missionId) {
        Mission mission = this.missionService.getMissions().get(missionId);

        if (mission == null) {
            this.messageConfig.missionDoesNotExist.send(player);
            return;
        }

        MissionProgress missionProgress = this.missionService.resetMission(missionId);
        missionProgress.setResetDate(Instant.now().plus(mission.getResetTime()));
        missionProgress.setTimeToReset(Duration.between(Instant.now(), missionProgress.getResetDate()));

        this.messageConfig.missionReset
                .with("mission-name", mission.getName())
                .send(player);
    }

    @Async
    @Permission("dream-missions.reload")
    @Executor(path = "reload", description = "Przeladowuje konfiguracje.")
    BukkitNotice reload() {
        final long time = System.currentTimeMillis();

        try {
            this.messageConfig.load();
            this.pluginConfig.load();

            return this.messageConfig.reloaded.with(
                    "time",
                    TimeUtil.format(System.currentTimeMillis() - time)
            );
        }
        catch (NullPointerException | OkaeriException exception) {
            exception.printStackTrace();
            return this.messageConfig.reloadError.with("error", exception.getMessage());
        }
    }

}