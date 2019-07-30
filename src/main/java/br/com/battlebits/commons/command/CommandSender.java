package br.com.battlebits.commons.command;

import br.com.battlebits.commons.translate.Language;
import net.md_5.bungee.api.chat.BaseComponent;

import java.util.UUID;

public interface CommandSender {

    UUID getUniqueId();

    void sendMessage(String str);

    void sendMessage(BaseComponent str);

    void sendMessage(BaseComponent[] fromLegacyText);

    Language getLanguage();
}
