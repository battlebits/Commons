package br.com.battlebits.commons.bukkit.event.account;

import br.com.battlebits.commons.bukkit.account.BukkitAccount;
import br.com.battlebits.commons.bukkit.event.PlayerCancellableEvent;
import br.com.battlebits.commons.translate.Language;
import lombok.Getter;
import org.bukkit.entity.Player;

@Getter
public class PlayerLanguageEvent extends PlayerCancellableEvent {
    private BukkitAccount bukkitPlayer;
    private Language language;

    public PlayerLanguageEvent(Player p, BukkitAccount player, Language language) {
        super(p);
        this.bukkitPlayer = player;
        this.language = language;
    }
}
