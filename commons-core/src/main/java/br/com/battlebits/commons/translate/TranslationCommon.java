package br.com.battlebits.commons.translate;

import br.com.battlebits.commons.CommonsConst;
import br.com.battlebits.commons.backend.DataTranslation;

import java.text.MessageFormat;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class TranslationCommon {

    private static TranslationCommon instance;

    private EnumMap<Language, Map<Enum<?>, MessageFormat>> languageTranslations;

    public TranslationCommon() {
        this.languageTranslations = new EnumMap<>(Language.class);
    }

    public void onEnable() {
        instance = this;
    }

    public void onDisable() {
        instance = null;
        this.languageTranslations.clear();
    }

    public void addTranslation(DataTranslation dataTranslation) {
        this.languageTranslations.putAll(dataTranslation.loadTranslations());
    }

    public String translate(Language language, final Enum<?> tag, final Object... format) {
        Map<Enum<?>, MessageFormat> map = languageTranslations.computeIfAbsent(language, v -> new HashMap<>());
        MessageFormat messageFormat = map.computeIfAbsent(tag, v -> new MessageFormat(tag.toString()));
        return messageFormat.format(format);
    }

    public static String tl(Enum<?> tag, Object... format) {
        return tl(CommonsConst.DEFAULT_LANGUAGE, tag, format);
    }

    public static String tl(Language language, Enum<?> tag, final Object... format) {
        if (instance == null) {
            return "INSTANCE NOT ENABLED";
        }
        return instance.translate(language, tag, format);
    }
}
