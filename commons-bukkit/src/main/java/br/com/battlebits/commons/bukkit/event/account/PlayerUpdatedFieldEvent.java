package br.com.battlebits.commons.bukkit.event.account;

import br.com.battlebits.commons.bukkit.account.BukkitAccount;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

@Getter
public class PlayerUpdatedFieldEvent extends PlayerEvent {

    private static final HandlerList handlers = new HandlerList();

    private BukkitAccount bukkitPlayer;
    private String field;
    @Setter
    private Object object;

    public PlayerUpdatedFieldEvent(Player p, BukkitAccount player, String field, Object object) {
        super(p);
        this.bukkitPlayer = player;
        this.field = field;
        this.object = object;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
