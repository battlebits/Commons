package br.com.battlebits.commons.command;

import br.com.battlebits.commons.account.Group;
import br.com.battlebits.commons.translate.Language;

import java.util.UUID;

public interface CommandSender {

    UUID getUniqueId();

    void sendMessage(String str);

    Language getLanguage();

    boolean hasGroupPermission(Group g);
}
