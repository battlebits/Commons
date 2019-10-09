package br.com.battlebits.commons.backend.model;

import br.com.battlebits.commons.account.AccountConfiguration;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModelAccountConfiguration {
    private boolean ignoreAll;
    private boolean tellEnabled;
    private boolean staffChatEnabled;
    private boolean partyInvites;

    public ModelAccountConfiguration(AccountConfiguration config) {
        this.ignoreAll = config.isIgnoreAll();
        this.tellEnabled = config.isTellEnabled();
        this.staffChatEnabled = config.isStaffChatEnabled();
        this.partyInvites = config.isPartyInvites();
    }
}
