package br.com.battlebits.commons.bukkit.listener;

import br.com.battlebits.commons.Commons;
import br.com.battlebits.commons.account.BattleAccount;
import br.com.battlebits.commons.backend.DataServer;
import br.com.battlebits.commons.backend.logging.DataLogType;
import br.com.battlebits.commons.backend.model.ModelAccount;
import br.com.battlebits.commons.bukkit.BukkitMain;
import br.com.battlebits.commons.bukkit.account.BukkitAccount;
import br.com.battlebits.commons.bukkit.event.account.PlayerUpdateFieldEvent;
import br.com.battlebits.commons.bukkit.event.account.PlayerUpdatedFieldEvent;
import br.com.battlebits.commons.party.Party;
import br.com.battlebits.commons.team.Team;
import com.comphenix.protocol.ProtocolManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Objects;
import java.util.UUID;

import static br.com.battlebits.commons.translate.TranslateTag.*;
import static br.com.battlebits.commons.translate.TranslationCommon.tl;

public class AccountListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public synchronized void onAsyncPreLogin(AsyncPlayerPreLoginEvent event) {
        if (Bukkit.getPlayer(event.getUniqueId()) != null) {
            event.setLoginResult(AsyncPlayerPreLoginEvent.Result.KICK_OTHER);
            event.setKickMessage(tl(ACCOUNT_ALREADY_ONLINE));
            return;
        }
        try{
            ModelAccount model = Commons.getDataAccount().getAccount(event.getUniqueId());
            BukkitAccount account;
            if (model == null) {
                account = new BukkitAccount(event.getUniqueId(), event.getName());
                Commons.getDataAccount().saveAccount(account);
            } else
                account = new BukkitAccount(model);
            account.setJoinData(event.getAddress().getHostName());
            Commons.getAccountCommon().loadBattleAccount(account);
            Commons.getDataServer().joinPlayer(account.getUniqueId());
            loadTeam(account);
            loadParty(account.getUniqueId());
        } catch (Exception e) {
            event.setKickMessage(tl(ACCOUNT_LOAD_FAILED));
            e.printStackTrace();
        }

    }

    private void loadParty(UUID uniqueId) {
        Party party = Commons.getPartyCommon().getByOwner(uniqueId);
        if (party == null) {
            return;
        }
    }


    private void loadTeam(BattleAccount account) {
        if (account.getTeamUniqueId() == null)
            return;
        Team team = Commons.getDataTeam().getTeam(account.getTeamUniqueId());
        if (team == null) {
            account.setTeamUniqueId(null);
            return;
        }
        team.updateAccount(account);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onRemoveAccount(AsyncPlayerPreLoginEvent event) {
        if (event.getLoginResult() != AsyncPlayerPreLoginEvent.Result.ALLOWED) {
            removePlayer(event.getUniqueId());
            return;
        }
        if (Commons.getAccount(event.getUniqueId()) == null) {
            event.setLoginResult(AsyncPlayerPreLoginEvent.Result.KICK_OTHER);
            event.setKickMessage(tl(ACCOUNT_NOT_LOADED));
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onLogin(PlayerLoginEvent event) {
        if (event.getResult() != org.bukkit.event.player.PlayerLoginEvent.Result.ALLOWED)
            return;
        if (Commons.getAccount(event.getPlayer().getUniqueId()) == null) {
            event.disallow(org.bukkit.event.player.PlayerLoginEvent.Result.KICK_OTHER, tl(ACCOUNT_NOT_LOADED));
            return;
        }
        /**
         * Need to use this because of protocol and just logging after making sure
         */
        int protocolVersion = BukkitMain.getInstance().getProtocolManager().getProtocolVersion(event.getPlayer());
        new BukkitRunnable() {

            @Override
            public void run() {
                Commons.getDataServer().joinPlayer(event.getPlayer().getUniqueId());
                Commons.getDataLog().log(DataLogType.PLAYER_JOIN, event.getPlayer().getUniqueId(), protocolVersion);
            }
        }.runTaskAsynchronously(BukkitMain.getInstance());
    }


    @EventHandler(priority = EventPriority.MONITOR)
    public void onJoin(PlayerJoinEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();

        /* Party */
        Party party = Commons.getPartyCommon().getByOwner(uuid);
        if (party == null) {
            party = Commons.getPartyCommon().getParty(uuid);
            if (party != null)
                party.onMemberJoin(uuid);
        } else {
            party.onOwnerJoin();
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onLeave(PlayerQuitEvent event) {
        int protocolVersion = BukkitMain.getInstance().getProtocolManager().getProtocolVersion(event.getPlayer());
        Bukkit.getScheduler().runTaskAsynchronously(BukkitMain.getInstance(), () -> {
            removePlayer(event.getPlayer().getUniqueId());
            Commons.getDataLog().log(DataLogType.PLAYER_LEAVE, event.getPlayer().getUniqueId(), protocolVersion);
        });
    }


    private void removePlayer(UUID uniqueId) {
        BukkitAccount account = BukkitAccount.getAccount(uniqueId);
        if (account == null)
            return;
        handleTeamLeave(account);
        handlePartyLeave(uniqueId);

        Commons.getDataServer().leavePlayer(account.getUniqueId());
        Commons.getAccountCommon().unloadBattleAccount(uniqueId);
    }

    private void handleTeamLeave(BattleAccount account) {
        if (account.getTeam() != null) {
            Team team = account.getTeam();
            boolean removeTeam = true;
            for (UUID uuid : team.getParticipants().keySet()) {
                if (uuid.equals(account.getUniqueId()))
                    continue;
                Player p = Bukkit.getPlayer(uuid);
                if (p != null && p.isOnline()) {
                    removeTeam = false;
                    break;
                }
            }
            if (removeTeam) {
                Commons.getTeamCommon().unloadTeam(account.getTeamUniqueId());
            }
        }
    }

    private void handlePartyLeave(UUID uniqueId) {
        Party party = Commons.getPartyCommon().getByOwner(uniqueId);
        if (party == null) {
            party = Commons.getPartyCommon().getParty(uniqueId);
            if (party != null) {
                party.onMemberLeave(uniqueId);
                if (party.getOnlineCount() == 0)
                    Commons.getPartyCommon().removeParty(party);
            }
        } else {
            party.onOwnerLeave();
            if (party.getOnlineCount() == 0)
                Commons.getPartyCommon().removeParty(party);
        }
    }

    @EventHandler
    public void onUpdateField(PlayerUpdateFieldEvent event) {
        BukkitAccount battlePlayer = event.getBukkitPlayer();
        switch (event.getField()) {
            default:
                //TODO: add update field
        }
    }

    @EventHandler
    public void onUpdatedField(PlayerUpdatedFieldEvent event) {
        BukkitAccount battlePlayer = event.getBukkitPlayer();
        switch (event.getField()) {
            default:
                //TODO: add updated fields
        }
    }
}
