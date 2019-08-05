package br.com.battlebits.commons.bukkit.command;

import br.com.battlebits.commons.account.Group;
import br.com.battlebits.commons.bukkit.account.BukkitAccount;
import br.com.battlebits.commons.command.CommandSender;
import br.com.battlebits.commons.translate.Language;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.UUID;

import static br.com.battlebits.commons.translate.TranslationCommon.tl;

@AllArgsConstructor
@Getter
public class BukkitCommandSender implements CommandSender {

    private org.bukkit.command.CommandSender commandSender;

    @Override
    public UUID getUniqueId() {
        if (commandSender instanceof Player) {
            return ((Player) commandSender).getUniqueId();
        }
        return UUID.randomUUID();
    }

    @Override
    public void sendMessage(String tag, Object... objects) {
        commandSender.sendMessage(tl(getLanguage(), tag, objects));
    }

    @Override
    public Language getLanguage() {
        return null;
    }

    @Override
    public boolean hasGroupPermission(Group g) {
        BukkitAccount account = BukkitAccount.getAccount(this.getUniqueId());
        if(account != null)
            return account.hasGroupPermission(g);
        return true; // Console
    }
}
