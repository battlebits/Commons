package br.com.battlebits.commons.bukkit.account;

import br.com.battlebits.commons.Commons;
import br.com.battlebits.commons.account.BattleAccount;
import br.com.battlebits.commons.backend.model.ModelAccount;
import br.com.battlebits.commons.bukkit.BukkitMain;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.UUID;

import static br.com.battlebits.commons.translate.TranslationCommon.tl;

public class BukkitAccount extends BattleAccount {

    private Player player;

    public BukkitAccount(ModelAccount model) {
        super(model);
    }

    public BukkitAccount(UUID uniqueId, String name) {
        super(uniqueId, name);
    }


    public Player getPlayer() {
        if(player == null)
            this.player = BukkitMain.getInstance().getServer().getPlayer(this.getUniqueId());
        return player;
    }

    public static BukkitAccount getAccount(UUID uuid) {
        return(BukkitAccount) Commons.getAccount(uuid);
    }

    @Override
    public void sendMessage(String tag, Object... objects) {
        player.sendMessage(tl(getLanguage(), tag, objects));
    }
}
