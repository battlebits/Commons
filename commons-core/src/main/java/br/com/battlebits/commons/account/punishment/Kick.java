package br.com.battlebits.commons.account.punishment;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by gustavo on 28/05/17.
 */

@Getter
public class Kick {
    private String server;
    private long kickTime;
    private String reason;

    public Kick(String server, String reason) {
        this(server, reason, System.currentTimeMillis());
    }

    public Kick(String server, String reason, long kickTime) {
        this.server = server;
        this.reason = reason;
        this.kickTime = kickTime;
    }




}
