package br.com.battlebits.commons.translate;

import br.com.battlebits.commons.CommonsConst;
import br.com.battlebits.commons.backend.DataTranslation;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

public class TranslationCommon {

    private static TranslationCommon instance;

    private Map<Language, Map<String, MessageFormat>> languageTranslations;
    private DataTranslation storage;

    public TranslationCommon(DataTranslation storage) {
        this.storage = storage;
        reloadTranslations();
    }

    public void onEnable() {
        instance = this;
    }

    public void onDisable() {
        instance = null;
        this.languageTranslations.clear();
    }

    public void reloadTranslations() {
        this.languageTranslations = storage.loadTranslations();
    }

    public String translate(Language language, final String tag, final Object... format) {
        Map<String, MessageFormat> map = languageTranslations.computeIfAbsent(language, v -> new HashMap<>());
        MessageFormat messageFormat = map.computeIfAbsent(tag, v -> new MessageFormat(""));
        return messageFormat.format(format);
    }

    public static String tl(String tag, Object... format) {
        return tl(CommonsConst.DEFAULT_LANGUAGE, tag, format);
    }

    public static String tl(Language language, String tag, final Object... format) {
        if (instance == null) {
            return "";
        }
        return instance.translate(language, tag, format);
    }
}
