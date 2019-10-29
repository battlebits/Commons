package br.com.battlebits.commons.bukkit.manager;

import br.com.battlebits.commons.Commons;
import br.com.battlebits.commons.CommonsConst;
import br.com.battlebits.commons.account.BattleAccount;
import br.com.battlebits.commons.account.Group;
import br.com.battlebits.commons.account.punishment.Ban;
import br.com.battlebits.commons.account.punishment.Mute;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static br.com.battlebits.commons.translate.TranslateTag.*;
import static br.com.battlebits.commons.translate.TranslationCommon.tl;

public class PunishManager {

    private Cache<String, Map.Entry<UUID, Ban>> banCache;

    public PunishManager() {
        banCache = CacheBuilder.newBuilder().expireAfterWrite(30L, TimeUnit.MINUTES).build(new CacheLoader<String, Map.Entry<UUID, Ban>>() {
            @Override
            public Map.Entry<UUID, Ban> load(String key) throws Exception {
                return null;
            }
        });
    }


    public void ban(BattleAccount player, Ban ban) {
        player.getPunishmentHistory().getBanHistory().add(ban);
        for (Player online : Bukkit.getOnlinePlayers()) {
            BattleAccount battleAccount = Commons.getAccountCommon().getBattleAccount(online.getUniqueId());
            if (battleAccount != null && battleAccount.hasGroupPermission(Group.ADMIN)) {
                String banSuccess = "";
                if (ban.isPermanent()) {
                    banSuccess = tl(COMMAND_BAN_PREFIX) + tl(COMMAND_BAN_SUCCESS, player.getUniqueId().toString().replace("-", ""), ban.getBannedBy(), ban.getDuration());
                } else {
                    banSuccess = tl(COMMAND_TEMPBAN_PREFIX) + tl(COMMAND_TEMPBAN_SUCCESS, player.getUniqueId().toString().replace("-", ""), ban.getBannedBy(), ban.getDuration());
                }
                online.sendMessage(banSuccess);
            }
            Commons.getDataAccount().saveAccount(player, "punishmentHistory");
            Bukkit.getPlayer(player.getUniqueId()).kickPlayer(tl(COMMAND_BAN_KICK, ban.getBannedBy(), ban.getReason(), ban.getDuration(), CommonsConst.FORUM_WEBSITE, CommonsConst.WEBSITE));
        }
    }

    public void unban(BattleAccount bannedByPlayer, BattleAccount player, Ban currentBan) {
        if (bannedByPlayer != null) {
            currentBan.unban(bannedByPlayer);
        } else {
            currentBan.unban();
        }
        for (Player online : Bukkit.getOnlinePlayers()) {
            BattleAccount battleAccount = Commons.getAccountCommon().getBattleAccount(online.getUniqueId());
            if (battleAccount.hasGroupPermission(Group.ADMIN)) {
                String unbanSuccess = tl(COMMAND_UNBAN_PREFIX) + tl(COMMAND_UNBAN_SUCCESS, player.getUniqueId().toString().replace("-", ""), currentBan.getUnbannedBy());
                online.sendMessage(unbanSuccess);
            }
        }
        Commons.getDataAccount().saveAccount(player, "punishmentHistory");
    }

    public void mute(BattleAccount player, Mute mute) {
        player.getPunishmentHistory().getMuteHistory().add(mute);
        for (Player online : Bukkit.getOnlinePlayers()) {
            BattleAccount battleAccount = Commons.getAccount(online.getUniqueId());
            if (battleAccount.hasGroupPermission(Group.ADMIN)) {
                String muteSuccess = "";
                if (mute.isPermanent()) {
                    muteSuccess = tl(battleAccount.getLanguage(), COMMAND_MUTE_PREFIX) + tl(battleAccount.getLanguage(), COMMAND_MUTE_SUCCESS);
                } else {
                    muteSuccess = tl(battleAccount.getLanguage(), COMMAND_TEMPBAN_PREFIX) + tl(battleAccount.getLanguage(), COMMAND_TEMPMUTE_SUCCESS);
                }
                online.sendMessage(muteSuccess);
            }
            Commons.getDataAccount().saveAccount(player, "punishmentHistory");
        }
    }

    public void unmute(BattleAccount mutedByPlayer, BattleAccount player, Mute currentMute) {
        if (mutedByPlayer != null) {
            currentMute.unmute(mutedByPlayer);
        } else {
            currentMute.unmute();
        }
        for (Player online : Bukkit.getOnlinePlayers()) {
            BattleAccount battleAccount = Commons.getAccountCommon().getBattleAccount(online.getUniqueId());
            if (battleAccount.hasGroupPermission(Group.ADMIN)) {
                String unmuteSuccess = tl(COMMAND_UNMUTE_PREFIX) + tl(COMMAND_UNMUTE_PREFIX, player.getUniqueId().toString().replace("-", ""), currentMute.getUnmutedBy());
                online.sendMessage(unmuteSuccess);
            }
        }
        Commons.getDataAccount().saveAccount(player, "punishmentHistory");
    }
}
