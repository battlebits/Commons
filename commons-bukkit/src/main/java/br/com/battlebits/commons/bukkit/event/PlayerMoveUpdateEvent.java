package br.com.battlebits.commons.bukkit.event;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 * Classe criada em 17/06/17.
 * Desenvolvido por:
 *
 * @author Luãn Pereira.
 */
public class PlayerMoveUpdateEvent extends PlayerMoveEvent {

    public PlayerMoveUpdateEvent(Player player, Location from, Location to) {
        super(player, from, to);
    }

    private static final HandlerList handlers = new HandlerList();

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}
