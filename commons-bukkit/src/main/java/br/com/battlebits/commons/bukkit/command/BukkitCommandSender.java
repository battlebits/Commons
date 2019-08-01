package br.com.battlebits.commons.bukkit.command;

import br.com.battlebits.commons.command.CommandSender;
import br.com.battlebits.commons.translate.Language;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.UUID;

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
    public void sendMessage(String str) {
        commandSender.sendMessage(str);
    }

    @Override
    public Language getLanguage() {
        return null;
    }
}
