package br.com.battlebits.commons.translate;

import br.com.battlebits.commons.backend.DataTranslation;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class TranslationCommon {

    private static TranslationCommon instance;

    private Set<Class<? extends Enum>> enums;
    private Map<Language, Map<String, MessageFormat>> languageTranslations;

    public TranslationCommon() {
    }

    public void onEnable() {
        this.enums = new HashSet<>();
        this.languageTranslations = new HashMap<>();
        instance = this;
    }

    public void onDisable() {
        instance = null;
        this.languageTranslations.clear();
    }

    public void addTranslation(DataTranslation dataTranslation) {
        final Map<Language, Map<String, MessageFormat>> map = dataTranslation.loadTranslations();
        map.forEach((language, messagesMap) -> {
            if(languageTranslations.containsKey(language)) {
                final Map<String, MessageFormat> messages = languageTranslations.get(language);
                messagesMap.forEach(messages::put);
            } else {
                languageTranslations.put(language, messagesMap);
            }
        });
        this.enums.add(dataTranslation.getEnum());
    }

    public String translate(Language language, final Enum<?> tag, final Object... format) {
        Map<String, MessageFormat> map = languageTranslations.computeIfAbsent(language, v -> new HashMap<>());
        MessageFormat messageFormat = map.computeIfAbsent(tag.toString(), v -> new MessageFormat(tag.toString()));
        return messageFormat.format(format);
    }

    public static String tl(Enum<?> tag, Object... format) {
        return tl(Language.PORTUGUESE, tag, format);
    }

    public static String tl(Language language, Enum<?> tag, final Object... format) {
        if (instance == null) {
            return "INSTANCE NOT ENABLED";
        }
        return instance.translate(language, tag, format);
    }

    public String translate(Language language, final String tag, final Object... format) {
        Map<String, MessageFormat> map = languageTranslations.computeIfAbsent(language, v -> new HashMap<>());
        MessageFormat messageFormat = map.computeIfAbsent(tag.toString(), v -> new MessageFormat(tag));
        return messageFormat.format(format);
    }

    public static String tl(String tag, Object... format) {
        return tl(Language.PORTUGUESE, tag, format);
    }

    public static String tl(Language language, String tag, final Object... format) {
        if (instance == null) {
            return "INSTANCE NOT ENABLED";
        }
        return instance.translate(language, tag, format);
    }

}
