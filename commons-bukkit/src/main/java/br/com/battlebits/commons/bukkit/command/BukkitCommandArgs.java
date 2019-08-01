package br.com.battlebits.commons.bukkit.command;

import br.com.battlebits.commons.command.CommandArgs;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BukkitCommandArgs extends CommandArgs {

    protected BukkitCommandArgs(CommandSender commandSender, String label, String[] args, int subCommand) {
        super(new BukkitCommandSender(commandSender), label, args, subCommand);
    }
    @Override
    public boolean isPlayer() {
        return ((BukkitCommandSender) getSender()).getCommandSender() instanceof Player;
    }

    public Player getPlayer() {
        if (!isPlayer()) {
            return null;
        }
        return (Player) ((BukkitCommandSender) getSender()).getCommandSender();
    }
}
