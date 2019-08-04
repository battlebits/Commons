package br.com.battlebits.commons.bukkit.party;

import br.com.battlebits.commons.party.Party;
import org.bukkit.Bukkit;

import java.util.UUID;

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
        //TODO: sendMessage using new translation system
    }
}
