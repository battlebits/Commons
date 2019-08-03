package br.com.battlebits.commons.bukkit.account;

import br.com.battlebits.commons.account.BattleAccount;
import br.com.battlebits.commons.backend.model.ModelAccount;
import lombok.Getter;
import org.bukkit.entity.Player;

import static br.com.battlebits.commons.translate.TranslationCommon.tl;

public class BukkitAccount extends BattleAccount {

    @Getter
    private Player player;

    public BukkitAccount(Player player, String ipAddress) {
        super(player.getUniqueId(), player.getName(), ipAddress);
        this.player = player;
    }

    public BukkitAccount(Player player, ModelAccount model) {
        super(model);
        this.player = player;
    }



    @Override
    public void sendMessage(String tag, Object... objects) {
        player.sendMessage(tl(getLanguage(), tag, objects));
    }
}
