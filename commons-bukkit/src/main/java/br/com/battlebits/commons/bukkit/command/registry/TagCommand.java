package br.com.battlebits.commons.bukkit.command.registry;

import br.com.battlebits.commons.Commons;
import br.com.battlebits.commons.CommonsConst;
import br.com.battlebits.commons.account.Tag;
import br.com.battlebits.commons.bukkit.BukkitMain;
import br.com.battlebits.commons.bukkit.account.BukkitAccount;
import br.com.battlebits.commons.bukkit.command.BukkitCommandArgs;
import br.com.battlebits.commons.command.CommandClass;
import br.com.battlebits.commons.command.CommandFramework;
import br.com.battlebits.commons.translate.Language;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import static br.com.battlebits.commons.translate.TranslateTag.*;
import static br.com.battlebits.commons.translate.TranslationCommon.tl;

public class TagCommand implements CommandClass {

    @CommandFramework.Command(name = "tag", runAsync = true)
    public void tag(BukkitCommandArgs cmdArgs) {
        if (cmdArgs.isPlayer()) {
            Player p = cmdArgs.getPlayer();
            String[] args = cmdArgs.getArgs();
            BukkitAccount player = (BukkitAccount) Commons.getAccountCommon().getBattleAccount(p.getUniqueId());
            String tagPrefix = tl(player.getLanguage(), COMMAND_TAG_PREFIX);
            if (!BukkitMain.getInstance().isTagControlEnabled()) {
                player.sendMessage(tagPrefix + tl(player.getLanguage(), COMMAND_TAG_NOT_ENABLED));
                return;
            }
            if (args.length == 0) {
                int max = player.getTags().size() * 2;
                TextComponent[] message = new TextComponent[max];
                message[0] = new TextComponent(tagPrefix + tl(player.getLanguage(), COMMAND_TAG_AVAILABLE));
                int i = max - 1;
                for (Tag t : player.getTags()) {
                    if (i < max - 1) {
                        message[i] = new TextComponent(ChatColor.WHITE + ", ");
                        i -= 1;
                    }
                    TextComponent component = new TextComponent((t == Tag.DEFAULT) ? (ChatColor.GRAY + "" + ChatColor.BOLD + "NORMAL") : ChatColor.getByChar(t.getColor()) + "" + ChatColor.BOLD + t.getPrefix());
                    component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponent[]{new TextComponent(tl(player.getLanguage(), COMMAND_TAG_SELECT))}));
                    component.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tag " + t.name()));
                    message[i] = component;
                    i -= 1;
                }
                p.spigot().sendMessage(message);
            } else {
                Tag tag = Tag.valueOf(args[0].toUpperCase());
                if (tag != null) {
                    if (player.getTags().contains(tag)) {
                        if (player.getTag() != tag) {
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    if (player.setTag(tag)) {
                                        p.sendMessage(tagPrefix + tl(player.getLanguage(), COMMAND_TAG_SELECTED));
                                    } else {
                                        p.sendMessage(tagPrefix + tl(player.getLanguage(), COMMAND_TAG_CHANGE_FAIL));
                                    }
                                }
                            }.runTask(BukkitMain.getInstance());
                        } else {
                            p.sendMessage(tagPrefix + tl(player.getLanguage(), COMMAND_TAG_CURRENT));
                        }
                    } else {
                        p.sendMessage(tagPrefix + tl(player.getLanguage(), COMMAND_TAG_NO_ACCESS));
                    }
                } else {
                    p.sendMessage(tagPrefix + tl(player.getLanguage(), COMMAND_TAG_NOT_FOUND));
                }
            }
        } else {
            cmdArgs.getSender().sendMessage(tl(CommonsConst.DEFAULT_LANGUAGE, SERVER_COMMAND_ONLY_FOR_PLAYER));
        }
    }
}
