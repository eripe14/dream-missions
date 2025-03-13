package cc.dreamcode.missions.mission;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

@AllArgsConstructor
public class MissionFinishEvent extends Event {

    private final static HandlerList HANDLER_LIST = new HandlerList();
    private final @Getter Mission mission;

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }
}