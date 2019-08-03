package br.com.battlebits.commons.bukkit.translate;

import br.com.battlebits.commons.translate.Language;
import br.com.battlebits.commons.translate.TranslationCommon;
import org.bukkit.ChatColor;

import java.text.MessageFormat;
import java.util.Map;

public class BukkitTranslationCommon extends TranslationCommon {

    public BukkitTranslationCommon(Map<Language, Map<String, MessageFormat>> languageTranslations) {
        super(languageTranslations);
    }

    @Override
    public String translate(Language language, String tag, Object... format) {
        return ChatColor.translateAlternateColorCodes('&', super.translate(language, tag, format));
    }
}
