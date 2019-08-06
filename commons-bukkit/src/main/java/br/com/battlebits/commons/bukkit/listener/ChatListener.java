package br.com.battlebits.commons.bukkit.listener;

import br.com.battlebits.commons.Commons;
import br.com.battlebits.commons.account.BattleAccount;
import br.com.battlebits.commons.account.Group;
import br.com.battlebits.commons.bukkit.api.chat.ChatAPI;
import br.com.battlebits.commons.translate.Language;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import static br.com.battlebits.commons.translate.TranslateTag.*;
import static br.com.battlebits.commons.translate.TranslationCommon.tl;

public class ChatListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        BattleAccount battlePlayer = Commons.getAccount(player.getUniqueId());
        Language l = battlePlayer.getLanguage();
        if (battlePlayer == null) {
            event.setCancelled(true);
            return;
        }
        switch (ChatAPI.getInstance().getChatState()) {
            case DISABLED:
                event.setCancelled(true);
                break;
            case STAFF:
                if (!battlePlayer.isStaff()) {
                    event.setCancelled(true);
                    break;
                }
                break;
            case INFLUENCER:
                if (battlePlayer.getServerGroup().ordinal() < Group.INFLUENCER.ordinal()) {
                    event.setCancelled(true);
                    break;
                }
                break;
            default:
                break;
        }
        if (event.isCancelled()) {
            player.sendMessage(l.tl(COMMAND_CHAT_PREFIX) + l.tl(COMMAND_CHAT_CANT_TALK));
        }
    }
}
