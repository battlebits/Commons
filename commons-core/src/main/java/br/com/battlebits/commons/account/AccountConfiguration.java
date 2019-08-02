package br.com.battlebits.commons.account;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AccountConfiguration {

    private boolean ignoreAll = false;
    private boolean tellEnabled = true;
    private boolean staffChatEnabled = false;
    private boolean partyInvites = true;

    protected transient BattleAccount account;

    public AccountConfiguration(BattleAccount account) {
        this.account = account;
    }

    public void setIgnoreAll(boolean ignoreAll) {
        if (this.ignoreAll != ignoreAll) {
            this.ignoreAll = ignoreAll;
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

}
