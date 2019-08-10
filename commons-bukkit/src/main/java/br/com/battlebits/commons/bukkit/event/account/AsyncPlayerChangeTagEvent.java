package br.com.battlebits.commons.bukkit.event.account;

import br.com.battlebits.commons.account.Tag;
import br.com.battlebits.commons.bukkit.event.PlayerCancellableEvent;
import lombok.Getter;
import org.bukkit.entity.Player;

@Getter
public class AsyncPlayerChangeTagEvent extends PlayerCancellableEvent {

    private Tag oldTag;
    private Tag newTag;
    private boolean isForced;

    public AsyncPlayerChangeTagEvent(Player p, Tag oldTag, Tag newTag, boolean isForced) {
        super(p, true);
        this.oldTag = oldTag;
        this.newTag = newTag;
        this.isForced = isForced;
    }

}
