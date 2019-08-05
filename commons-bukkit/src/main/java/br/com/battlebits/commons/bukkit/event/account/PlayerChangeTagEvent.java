package br.com.battlebits.commons.bukkit.event.account;

import br.com.battlebits.commons.account.Tag;
import br.com.battlebits.commons.bukkit.event.PlayerCancellableEvent;
import lombok.Getter;
import org.bukkit.entity.Player;

@Getter
public class PlayerChangeTagEvent extends PlayerCancellableEvent {

    private Tag oldTag;
    private Tag newTag;
    private boolean isForced;

    public PlayerChangeTagEvent(Player p, Tag oldTag, Tag newTag, boolean isForced) {
        super(p);
        this.oldTag = oldTag;
        this.newTag = newTag;
        this.isForced = isForced;
    }

}
