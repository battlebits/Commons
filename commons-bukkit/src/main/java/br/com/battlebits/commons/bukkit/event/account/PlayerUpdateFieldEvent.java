package br.com.battlebits.commons.bukkit.event.account;

import br.com.battlebits.commons.bukkit.account.BukkitAccount;
import br.com.battlebits.commons.bukkit.event.PlayerCancellableEvent;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

@Getter
public class PlayerUpdateFieldEvent extends PlayerCancellableEvent {

    private BukkitAccount bukkitPlayer;
    private String field;
    @Setter
    private Object object;

    public PlayerUpdateFieldEvent(Player p, BukkitAccount player, String field, Object object) {
        super(p);
        this.bukkitPlayer = player;
        this.field = field;
        this.object = object;
    }
}
