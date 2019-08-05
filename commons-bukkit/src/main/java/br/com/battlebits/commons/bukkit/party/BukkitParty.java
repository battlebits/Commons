package br.com.battlebits.commons.bukkit.party;

import br.com.battlebits.commons.Commons;
import br.com.battlebits.commons.account.BattleAccount;
import br.com.battlebits.commons.party.Party;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import java.util.UUID;
import java.util.stream.Stream;
import static br.com.battlebits.commons.translate.TranslateTag.*;
import static br.com.battlebits.commons.translate.TranslationCommon.tl;

public class BukkitParty extends Party {

    public BukkitParty(UUID uuid) {
        super(uuid);
    }

    @Override
    public void init() {

    }

    @Override
    public void onOwnerJoin() {

    }

    @Override
    public void onOwnerLeave() {

    }

    @Override
    public int getOnlineCount() {
        int count = 0;
        if (Bukkit.getPlayer(getOwner()) != null) {
            count++;
        }
        for (UUID uuid : getMembers()) {
            if (Bukkit.getPlayer(uuid) != null) {
                count++;
            }
        }
        return count;
    }

    @Override
    public void onMemberJoin(UUID member) {

    }

    @Override
    public void onMemberLeave(UUID member) {

    }

    @Override
    public void sendMessage(boolean prefix, boolean translate, String id, String[]... replacement) {
        for (UUID uuid : Stream.concat(Stream.of(getOwner()), getMembers().stream()).toArray((UUID[]::new))) {
            Player player = Bukkit.getPlayer(uuid);
            if (player != null) {
                player.sendMessage((prefix ? PREFIX : "") + (translate ? tl(Commons.getAccountCommon().getBattleAccount(uuid).getLanguage(), id, replacement) : id));
            }
        }
    }
}
