package br.com.battlebits.commons.backend.translate;

import br.com.battlebits.commons.Commons;
import br.com.battlebits.commons.backend.DataTranslation;
import br.com.battlebits.commons.translate.Language;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class DataTranslationImpl implements DataTranslation {

    private Map<Language, Map<String, MessageFormat>> languageMaps;

    public DataTranslationImpl() {
        languageMaps = new EnumMap<>(Language.class);
    }

    @Override
    public void setup(String dirLocation) {
        this.languageMaps.clear();
        for (Language language : Language.values()) {
            try (InputStream inputStream = new FileInputStream(new File(dirLocation, language.getFileName()))) {
                Properties properties = new Properties();
                properties.load(inputStream);

                Map<String, MessageFormat> map = new HashMap<>();
                properties.forEach((key, message) -> map.put((String) key, new MessageFormat((String) message)));

                languageMaps.put(language, map);
            } catch (Exception e) {
                Commons.getLogger().warning("Failed to load " + language.name().toUpperCase());
                e.printStackTrace();
            }
        }
    }

    @Override
    public Map<Language, Map<String, MessageFormat>> loadTranslations() {
        return this.languageMaps;
    }

    @Override
    public Map<String, MessageFormat> loadTranslation(Language lang) {
        return this.languageMaps.getOrDefault(lang, new HashMap<>());
    }
}
