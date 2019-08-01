package br.com.battlebits.commons.account;

import br.com.battlebits.commons.account.BattleAccount;
import lombok.*;

@Data
@NoArgsConstructor
public class AccountConfiguration {

    private boolean ignoreAll = false;
    private boolean tellEnabled = true;
    private boolean canPlaySound = true;
    private boolean alertsEnabled = true;
    private boolean staffChatEnabled = false;
    private boolean clanChatEnabled = false;
    private boolean partyInvites = true;
    private boolean rankedEnabled = true;

    protected transient BattleAccount account;

    public AccountConfiguration(BattleAccount account) {
        this.account = account;
    }

    public void setCanPlaySound(boolean canPlaySound) {
        if (this.canPlaySound != canPlaySound) {
            this.canPlaySound = canPlaySound;
        }
    }

    public void setClanChatEnabled(boolean clanChatEnabled) {
        if (this.clanChatEnabled != clanChatEnabled) {
            this.clanChatEnabled = clanChatEnabled;
        }
    }

    public void setIgnoreAll(boolean ignoreAll) {
        if (this.ignoreAll != ignoreAll) {
            this.ignoreAll = ignoreAll;
        }
    }

    public void setAlertsEnabled(boolean showAlerts) {
        if (this.alertsEnabled != showAlerts) {
            this.alertsEnabled = showAlerts;
        }
    }

    public void setStaffChatEnabled(boolean staffChatEnabled) {
        if (this.staffChatEnabled != staffChatEnabled) {
            this.staffChatEnabled = staffChatEnabled;
        }
    }

    public void setTellEnabled(boolean tellEnabled) {
        if (this.tellEnabled != tellEnabled) {
            this.tellEnabled = tellEnabled;
        }
    }

    public void setPartyReceiveInvite(boolean tellEnabled) {
        if (this.tellEnabled != tellEnabled) {
            this.tellEnabled = tellEnabled;
        }
    }

    public void setRankedEnabled(boolean rankedEnabled) {
        if (this.rankedEnabled != rankedEnabled) {
            this.rankedEnabled = rankedEnabled;
        }
    }
}
