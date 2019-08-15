package br.com.battlebits.commons.bukkit.command.registry;

import br.com.battlebits.commons.Commons;
import br.com.battlebits.commons.account.BattleAccount;
import br.com.battlebits.commons.bukkit.command.BukkitCommandArgs;
import br.com.battlebits.commons.bukkit.inventory.MenuAccount;
import br.com.battlebits.commons.command.CommandClass;
import br.com.battlebits.commons.command.CommandFramework.Command;
import br.com.battlebits.commons.command.CommandSender;
import br.com.battlebits.commons.translate.Language;

import java.util.UUID;

import static br.com.battlebits.commons.translate.TranslateTag.COMMAND_ACCOUNT_PREFIX;
import static br.com.battlebits.commons.translate.TranslateTag.PLAYER_NOT_EXIST;
import static br.com.battlebits.commons.translate.TranslationCommon.tl;

public class AccountCommand implements CommandClass {

    @Command(name = "account", aliases = {"acc", "conta"}, runAsync = true)
    public void account(BukkitCommandArgs cmdArgs) {
        CommandSender sender = cmdArgs.getSender();
        String[] args = cmdArgs.getArgs();

        UUID uuid = cmdArgs.getPlayer().getUniqueId();

        if (args.length > 0)
            uuid = Commons.getUuidOf(args[0]);
        Language l = sender.getLanguage();
        String accountPrefix = l.tl(COMMAND_ACCOUNT_PREFIX);

        if (uuid == null) {
            sender.sendMessage(accountPrefix + l.tl(PLAYER_NOT_EXIST));
            return;
        }
        BattleAccount account = Commons.getOfflineAccount(uuid);
        if (account == null) {
            sender.sendMessage(accountPrefix + tl(l, PLAYER_NOT_EXIST));
            return;
        }

        MenuAccount menu = new MenuAccount(account, l);
        menu.open(cmdArgs.getPlayer());
    }
}
