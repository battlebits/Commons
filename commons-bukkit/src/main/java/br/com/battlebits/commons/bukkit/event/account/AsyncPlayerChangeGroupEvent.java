package br.com.battlebits.commons.bukkit.event.account;

import br.com.battlebits.commons.account.Group;
import br.com.battlebits.commons.bukkit.account.BukkitAccount;
import br.com.battlebits.commons.bukkit.event.PlayerCancellableEvent;
import lombok.Getter;
import org.bukkit.entity.Player;

@Getter
public class AsyncPlayerChangeGroupEvent extends PlayerCancellableEvent {
    private BukkitAccount bukkitAccount;
    private Group group;

    public AsyncPlayerChangeGroupEvent(Player p, BukkitAccount player, Group group) {
        super(p, true);
        this.bukkitAccount = player;
        this.group = group;
    }
}
