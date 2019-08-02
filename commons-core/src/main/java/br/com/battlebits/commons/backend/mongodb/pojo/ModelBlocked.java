package br.com.battlebits.commons.backend.mongodb.pojo;

import br.com.battlebits.commons.account.Blocked;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class ModelBlocked {

    private UUID uniqueId;
    private Long blockedTime;

    public ModelBlocked(Blocked b) {
        this.uniqueId = b.getUniqueId();
        this.blockedTime = b.getBlockedTime();
    }
}
