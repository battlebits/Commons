package br.com.battlebits.commons.backend.model;

import br.com.battlebits.commons.account.punishment.Ban;
import br.com.battlebits.commons.account.punishment.Kick;
import br.com.battlebits.commons.account.punishment.Mute;
import br.com.battlebits.commons.account.punishment.PunishmentHistory;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ModelPunishmentHistory {
    private List<Ban> banHistory;
    private List<Mute> muteHistory;
    private List<Kick> kickHistory;

    public ModelPunishmentHistory(PunishmentHistory history) {
        this.banHistory = history.getBanHistory();
        this.muteHistory = history.getMuteHistory();
        this.kickHistory = history.getKickHistory();
    }
}
