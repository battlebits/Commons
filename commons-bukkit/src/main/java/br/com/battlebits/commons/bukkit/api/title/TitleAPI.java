package br.com.battlebits.commons.bukkit.api.title;

import br.com.battlebits.commons.bukkit.BukkitMain;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers.TitleAction;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;

public class TitleAPI {

    public static void setTitle(Player player, String title, String subtitle, int fadeInTime, int stayTime,
                                int fadeOutTime, boolean ticks) {
        if (fadeInTime != -1 && fadeOutTime != -1 && stayTime != -1)
            setTimes(player, fadeInTime, stayTime, fadeOutTime, ticks);
        if (title != null && !title.isEmpty())
            setTitle(player, title);
        if (subtitle != null && !subtitle.isEmpty())
            setSubtitle(player, subtitle);
    }

    public static void setTitle(Player player, String title, String subtitle, int fadeInTime, int stayTime,
                                int fadeOutTime) {
        if (fadeInTime != -1 && fadeOutTime != -1 && stayTime != -1)
            setTimes(player, fadeInTime, stayTime, fadeOutTime, false);
        if (title != null && !title.isEmpty())
            setTitle(player, title);
        if (subtitle != null && !subtitle.isEmpty())
            setSubtitle(player, subtitle);
    }

    public static void setTitle(Player player, String title, String subtitle) {
        if (title != null && !title.isEmpty())
            setTitle(player, title);
        if (subtitle != null && !subtitle.isEmpty())
            setSubtitle(player, subtitle);
    }

    public static void setTitle(Player player, String title, int fadeInTime, int stayTime, int fadeOutTime) {

    }

    public static void setTitle(Player player, String string) {
        PacketContainer packet = new PacketContainer(PacketType.Play.Server.TITLE);
        packet.getTitleActions().write(0, TitleAction.TITLE);
        packet.getChatComponents().write(0, WrappedChatComponent.fromText(string));
        try {
            BukkitMain.getInstance().getProtocolManager().sendServerPacket(player, packet);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public static void setSubtitle(Player player, String string) {
        PacketContainer packet = new PacketContainer(PacketType.Play.Server.TITLE);
        packet.getTitleActions().write(0, TitleAction.SUBTITLE);
        packet.getChatComponents().write(0, WrappedChatComponent.fromText(string));
        try {
            BukkitMain.getInstance().getProtocolManager().sendServerPacket(player, packet);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public static void setTimes(Player player, int fadeInTime, int stayTime, int fadeOutTime, boolean ticks) {
        PacketContainer packet = new PacketContainer(PacketType.Play.Server.TITLE);
        packet.getTitleActions().write(0, TitleAction.TIMES);
        packet.getIntegers().write(0, fadeInTime * (ticks ? 1 : 20));
        packet.getIntegers().write(1, stayTime * (ticks ? 1 : 20));
        packet.getIntegers().write(2, fadeOutTime * (ticks ? 1 : 20));
        try {
            BukkitMain.getInstance().getProtocolManager().sendServerPacket(player, packet);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public static void resetTitle(Player player) {
        PacketContainer packet = new PacketContainer(PacketType.Play.Server.TITLE);
        packet.getTitleActions().write(0, TitleAction.RESET);
        try {
            BukkitMain.getInstance().getProtocolManager().sendServerPacket(player, packet);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public static void clearTitle(Player player) {
        PacketContainer packet = new PacketContainer(PacketType.Play.Server.TITLE);
        packet.getTitleActions().write(0, TitleAction.CLEAR);
        try {
            BukkitMain.getInstance().getProtocolManager().sendServerPacket(player, packet);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}
