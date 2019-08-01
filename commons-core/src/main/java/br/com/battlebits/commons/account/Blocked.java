package br.com.battlebits.commons.account;

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

}
