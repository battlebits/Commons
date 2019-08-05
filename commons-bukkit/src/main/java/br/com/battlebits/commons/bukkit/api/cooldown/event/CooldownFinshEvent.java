package br.com.battlebits.commons.bukkit.api.cooldown.event;

import br.com.battlebits.commons.bukkit.api.cooldown.types.Cooldown;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;


public class CooldownFinshEvent extends CooldownEvent {

    public CooldownFinshEvent(Player player, Cooldown cooldown) {
        super(player, cooldown);
    }

    private static final HandlerList handlers = new HandlerList();

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
