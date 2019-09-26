package br.com.battlebits.commons.bukkit.translate;

import br.com.battlebits.commons.backend.DataTranslation;
import br.com.battlebits.commons.translate.Language;
import br.com.battlebits.commons.translate.TranslationCommon;
import org.bukkit.ChatColor;

public class BukkitTranslationCommon extends TranslationCommon {

    public BukkitTranslationCommon(DataTranslation dataTranslation) {
        super();
        addTranslation(dataTranslation);
    }

    @Override
    public String translate(Language language, Enum<?> tag, Object... format) {
        return ChatColor.translateAlternateColorCodes('&', super.translate(language, tag, format));
    }
}
