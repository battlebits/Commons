package br.com.battlebits.commons.bukkit.api.actionbar;

import br.com.battlebits.commons.Commons;
import br.com.battlebits.commons.bukkit.BukkitMain;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;

import static br.com.battlebits.commons.translate.TranslationCommon.tl;

public class ActionBarAPI {

    public static void send(Player player, String text) {
        PacketContainer chatPacket = new PacketContainer(PacketType.Play.Server.CHAT);
        chatPacket.getChatComponents().write(0, WrappedChatComponent.fromJson("{\"text\":\"" + text + " \"}"));
        chatPacket.getBytes().write(0, (byte) 2);
        try {
            BukkitMain.getInstance().getProtocolManager().sendServerPacket(player, chatPacket);
        } catch (InvocationTargetException e) {
            throw new RuntimeException("Cannot send packet " + chatPacket, e);
        }
    }

    public static void broadcast(String text) {
        Bukkit.getOnlinePlayers().forEach(p -> send(p, text));
    }


    public static void broadcast(String id, Object... objects) {
        Bukkit.getOnlinePlayers().forEach(p -> send(p, tl(Commons.getAccount(p.getUniqueId()).getLanguage(), id, objects)));
    }

}
