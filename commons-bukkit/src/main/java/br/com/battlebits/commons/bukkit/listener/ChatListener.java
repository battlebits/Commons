package br.com.battlebits.commons.bukkit.listener;

import br.com.battlebits.commons.Commons;
import br.com.battlebits.commons.account.BattleAccount;
import br.com.battlebits.commons.account.Group;
import br.com.battlebits.commons.bukkit.BukkitMain;
import br.com.battlebits.commons.bukkit.account.BukkitAccount;
import br.com.battlebits.commons.bukkit.api.chat.ChatAPI;
import br.com.battlebits.commons.translate.Language;
import br.com.battlebits.commons.util.string.StringURLUtils;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

import static br.com.battlebits.commons.translate.TranslateTag.*;

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
        switch (ChatAPI.getChatState()) {
            case DISABLED:
                event.setCancelled(true);
                break;
            case STAFF:
                if (!battlePlayer.isStaff()) {
                    event.setCancelled(true);
                    break;
                }
                break;
            case CREATOR:
                if (battlePlayer.getServerGroup().ordinal() < Group.CREATOR.ordinal()) {
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

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onChat(AsyncPlayerChatEvent event) {
        event.setCancelled(true);
        BukkitAccount account = BukkitAccount.getAccount(event.getPlayer().getUniqueId());
        if (account == null) {
            event.setCancelled(true);
            return;
        }
        for (Player r : event.getRecipients()) {
            try {
                BukkitAccount receiver = BukkitAccount.getAccount(r.getUniqueId());
                if (receiver == null) {
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            Player p = Bukkit.getPlayer(r.getUniqueId());
                            if (p == null)
                                return;
                            if (!p.isOnline())
                                return;
                            p.kickPlayer("ERROR");
                        }
                    }.runTask(BukkitMain.getInstance());
                    continue;
                }
                if ((!receiver.getConfiguration().isIgnoreAll()) && (!receiver.getBlockedPlayers().containsKey(account.getUniqueId()) && (!account.getBlockedPlayers().containsKey(receiver.getUniqueId())))) {
                    TextComponent team = null;
                    int text = 2;
                    if (account.getTeam() != null) {
                        team = new TextComponent(ChatColor.GRAY + "[" + ChatColor.DARK_GRAY + account.getTeam().getAbbreviation() + ChatColor.GRAY + "] ");
                        team.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/team info " + account.getTeam().getName()));
                        TextComponent[] clanMessage = new TextComponent[]{ //
                                new TextComponent(receiver.getLanguage().tl(TEAM_HOVER_INFO, account.getTeam().getName(),//
                                        account.getTeam().getParticipants().size() + "/" + account.getTeam().getSlots()))};
                        team.setHoverEvent(new HoverEvent(net.md_5.bungee.api.chat.HoverEvent.Action.SHOW_TEXT, clanMessage));
                        text += 1;
                    }
                    TextComponent[] textTo = new TextComponent[text + event.getMessage().split(" ").length];
                    ChatColor color = ChatColor.getByChar(account.getTag().getColor());
                    String tag = color + "" + ChatColor.BOLD + account.getTag().getPrefix();
                    TextComponent cmdAccount = new TextComponent(tag + (ChatColor.stripColor(tag).trim().length() > 0 ? " " : "") + color + event.getPlayer().getName());
                    cmdAccount.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/account " + account.getName()));
                    cmdAccount.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText(receiver.getLanguage().tl(ACCOUNT_HOVER_INFO))));
                    int i = 0;
                    if (team != null) {
                        textTo[i] = team;
                        ++i;
                    }
                    textTo[i] = cmdAccount;
                    ++i;
                    textTo[i] = new TextComponent(ChatColor.WHITE + ":");
                    ++i;
                    for (String msg : event.getMessage().split(" ")) {
                        msg = " " + msg;
                        TextComponent text2 = new TextComponent(msg);
                        List<String> url = StringURLUtils.extractUrls(msg);
                        if (url.size() > 0) {
                            text2.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, url.get(0)));
                        }
                        textTo[i] = text2;
                        ++i;
                    }
                    r.spigot().sendMessage(textTo);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        BukkitMain.getInstance().getLogger().info("<" + account.getName() + "> " + event.getMessage());
    }

}
