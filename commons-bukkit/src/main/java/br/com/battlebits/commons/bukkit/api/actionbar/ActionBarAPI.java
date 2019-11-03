package br.com.battlebits.commons.bukkit.api.actionbar;

import br.com.battlebits.commons.Commons;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import static br.com.battlebits.commons.translate.TranslationCommon.tl;

public class ActionBarAPI {

    public static void send(Player player, String text) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new ComponentBuilder(text).create());
    }

    public static void broadcast(String text) {
        Bukkit.getOnlinePlayers().forEach(p -> send(p, text));
    }


    public static void broadcast(String id, Object... objects) {
        Bukkit.getOnlinePlayers().forEach(p -> send(p, tl(Commons.getAccount(p.getUniqueId()).getLanguage(), id, objects)));
    }

}
