package br.com.battlebits.commons.bukkit.event;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerCancellableEvent extends Event implements Cancellable {

    @Getter
    protected Player player;
    private static final HandlerList handlers = new HandlerList();
    private boolean cancelled = false;

    public PlayerCancellableEvent(Player who) {
        this(who, false);
    }

    public PlayerCancellableEvent(Player who, boolean async) {
        super(async);
        player = who;
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
