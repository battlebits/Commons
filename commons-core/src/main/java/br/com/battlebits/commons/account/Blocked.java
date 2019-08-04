package br.com.battlebits.commons.account;

import br.com.battlebits.commons.backend.model.ModelBlocked;
import lombok.Getter;

import java.util.UUID;

@Getter
public class Blocked {

    private UUID uniqueId;
    private Long blockedTime;

    public Blocked(UUID blockedPlayer) {
        this.blockedTime = System.currentTimeMillis();
        this.uniqueId = blockedPlayer;
    }

    public Blocked(ModelBlocked model) {
        this.uniqueId = model.getUniqueId();
        this.blockedTime = model.getBlockedTime();
    }

}
