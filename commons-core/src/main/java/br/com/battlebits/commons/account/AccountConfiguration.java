package br.com.battlebits.commons.account;

import br.com.battlebits.commons.Commons;
import br.com.battlebits.commons.backend.DataAccount;
import br.com.battlebits.commons.backend.model.ModelAccountConfiguration;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AccountConfiguration {

    private boolean ignoreAll = false;
    private boolean tellEnabled = true;
    private boolean staffChatEnabled = false;
    private boolean partyInvites = true;

    private BattleAccount account;

    private static DataAccount STORAGE = Commons.getDataAccount();

    public AccountConfiguration(BattleAccount account, ModelAccountConfiguration config) {
        this.account = account;
        this.ignoreAll = config.isIgnoreAll();
        this.tellEnabled = config.isTellEnabled();
        this.staffChatEnabled = config.isStaffChatEnabled();
        this.partyInvites = config.isPartyInvites();
    }

    public AccountConfiguration(BattleAccount account) {
        this.account = account;
    }

    public void setIgnoreAll(boolean ignoreAll) {
        if (this.ignoreAll != ignoreAll) {
            this.ignoreAll = ignoreAll;
            STORAGE.saveConfiguration(account, "ignoreAll");
        }
    }

    public void setStaffChatEnabled(boolean staffChatEnabled) {
        if (this.staffChatEnabled != staffChatEnabled) {
            this.staffChatEnabled = staffChatEnabled;
            STORAGE.saveConfiguration(account, "staffChatEnabled");
        }
    }

    public void setTellEnabled(boolean tellEnabled) {
        if (this.tellEnabled != tellEnabled) {
            this.tellEnabled = tellEnabled;
            STORAGE.saveConfiguration(account, "tellEnabled");
        }
    }

    public void setPartyReceiveInvite(boolean partyInvites) {
        if (this.partyInvites != tellEnabled) {
            this.partyInvites = tellEnabled;
            STORAGE.saveConfiguration(account, "partyInvites");
        }
    }

}
