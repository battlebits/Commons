package br.com.battlebits.commons.backend.mongodb.pojo;

import br.com.battlebits.commons.account.punishment.Ban;
import br.com.battlebits.commons.account.punishment.Kick;
import br.com.battlebits.commons.account.punishment.Mute;
import lombok.Data;

import java.util.List;

@Data
public class ModelPunishmentHistory {
    private List<Ban> banHistory;
    private List<Mute> muteHistory;
    private List<Kick> kickHistory;
}
