package br.com.battlebits.commons.bukkit.command.registry;

import br.com.battlebits.commons.account.Group;
import br.com.battlebits.commons.command.CommandArgs;
import br.com.battlebits.commons.command.CommandClass;
import br.com.battlebits.commons.command.CommandFramework;
import net.md_5.bungee.api.ChatColor;

public class TestCommand implements CommandClass {

    @CommandFramework.Command(name = "teste", usage = "/<command>", groupToUse = Group.DEFAULT, noPermMessageId = TranslateTag.COMMAND_NO_PERMISSION)
    public void teste(CommandArgs args) {
        args.getSender().sendMessage(ChatColor.GREEN + "Olá mundo!");
    }
}
