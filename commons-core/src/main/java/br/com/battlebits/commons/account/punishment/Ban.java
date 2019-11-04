package br.com.battlebits.commons.account.punishment;

import br.com.battlebits.commons.account.BattleAccount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.UUID;

@Data
@AllArgsConstructor
public class Ban {

    private String bannedBy;
    private UUID bannedByUniqueId;
    private String bannedIp;

    private String server;

    private long banTime;
    private String reason;

    private boolean unbanned;
    private String unbannedBy;
    private UUID unbannedByUniqueId;
    private long unbanTime;

    private long expire;
    private long duration;

    public Ban(String bannedBy, String bannedIp, String server, String reason, long duration) {
        this(bannedBy, null, bannedIp, server, reason, duration);
    }

    public Ban(String bannedBy, UUID bannedByUuid, String bannedIp, String server, String reason, long expire) {
        this(bannedBy, bannedByUuid, bannedIp, server, System.currentTimeMillis(), reason, false, null, null, -1,
                expire, expire - System.currentTimeMillis());
    }

    public Ban(String bannedBy, String bannedIp, String server, String reason) {
        this(bannedBy, null, bannedIp, server, reason);
    }

    public Ban(String bannedBy, UUID bannedByUuid, String bannedIp, String server, String reason) {
        this(bannedBy, bannedByUuid, server, bannedIp, System.currentTimeMillis(), reason, false, null, null, -1, -1,
                -1);
    }

    public boolean hasExpired() {
        return expire != -1 && expire < System.currentTimeMillis();
    }

    public boolean isPermanent() {
        return expire == -1;
    }

    public void unban() {
        this.unbanned = true;
        this.unbannedBy = "CONSOLE";
        this.unbanTime = System.currentTimeMillis();
    }


    public void unban(BattleAccount unbanPlayer) {
        this.unbanned = true;
        this.unbannedBy = unbanPlayer.getName();
        this.unbannedByUniqueId = unbanPlayer.getUniqueId();
        this.unbanTime = System.currentTimeMillis();
    }

}
