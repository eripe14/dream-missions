package cc.dreamcode.missions.command;

import cc.dreamcode.command.CommandBase;
import cc.dreamcode.command.annotation.Command;
import cc.dreamcode.command.annotation.Executor;
import cc.dreamcode.command.annotation.Permission;
import cc.dreamcode.missions.mission.MissionMenu;
import eu.okaeri.injector.annotation.Inject;
import eu.okaeri.tasker.core.Tasker;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;

@Command(name = "missions")
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class MissionCommand implements CommandBase {

    private final Tasker tasker;
    private final MissionMenu menu;

    @Permission("dream-missions.menu")
    @Executor(description = "Otwiera menu.")
    void menu(Player player) {
        this.tasker.newSharedChain("dbops:" + player.getUniqueId())
                .supplyAsync(() -> this.menu.build(player))
                .acceptSync(bukkitMenu -> bukkitMenu.open(player))
                .execute();
    }

}