package br.com.battlebits.commons.backend.model;

import br.com.battlebits.commons.account.Blocked;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModelBlocked {

    private UUID uniqueId;
    private Long blockedTime;

    public ModelBlocked(Blocked b) {
        this.uniqueId = b.getUniqueId();
        this.blockedTime = b.getBlockedTime();
    }
}
