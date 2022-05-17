package uoc.tfm.vmejia.speedrun.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.UUID;

public class MarcadorEvent extends Event implements Cancellable {

    private static final HandlerList HANDLERS = new HandlerList();

    private final UUID uuid;
    private final String message;

    private boolean cancelled;

    public MarcadorEvent(UUID uuid, String message){
        this.uuid = uuid;
        this.message = message;
        this.cancelled = false;
    }

    public static HandlerList getHandlerList(){return HANDLERS;}

    public UUID getUuid(){return this.uuid;}
    public String getMessage(){return this.message;}

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }
}