package cc.dreamcode.missions.command;

import cc.dreamcode.command.CommandBase;
import cc.dreamcode.command.annotation.Async;
import cc.dreamcode.command.annotation.Command;
import cc.dreamcode.command.annotation.Executor;
import cc.dreamcode.command.annotation.Permission;
import cc.dreamcode.notice.bukkit.BukkitNotice;
import cc.dreamcode.missions.config.MessageConfig;
import cc.dreamcode.missions.config.PluginConfig;
import cc.dreamcode.utilities.TimeUtil;
import eu.okaeri.configs.exception.OkaeriException;
import eu.okaeri.injector.annotation.Inject;
import lombok.RequiredArgsConstructor;

@Command(name = "missions")
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class ReloadCommand implements CommandBase {

    private final PluginConfig pluginConfig;
    private final MessageConfig messageConfig;

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