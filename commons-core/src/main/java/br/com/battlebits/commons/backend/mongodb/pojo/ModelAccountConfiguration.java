package br.com.battlebits.commons.backend.mongodb.pojo;

import br.com.battlebits.commons.account.AccountConfiguration;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
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
