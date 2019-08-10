package br.com.battlebits.commons.bukkit.event.account;

import br.com.battlebits.commons.account.Group;
import br.com.battlebits.commons.bukkit.account.BukkitAccount;
import br.com.battlebits.commons.bukkit.event.PlayerCancellableEvent;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Getter
public class AsyncPlayerChangedGroupEvent extends Event {
    private BukkitAccount bukkitAccount;
    private static final HandlerList handlers = new HandlerList();

    public AsyncPlayerChangedGroupEvent(BukkitAccount player) {
        super(true);
        this.bukkitAccount = player;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
