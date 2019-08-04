package br.com.battlebits.commons.backend.properties;

import br.com.battlebits.commons.Commons;
import br.com.battlebits.commons.backend.DataTranslation;
import br.com.battlebits.commons.translate.Language;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertiesStorageDataTranslation implements DataTranslation {

    private File dirLocation;

    public PropertiesStorageDataTranslation(File dirLocation) {
        this.dirLocation = dirLocation;
    }

    @Override
    public Map<Language, Map<String, MessageFormat>> loadTranslations() {
        Map<Language, Map<String, MessageFormat>> languageMaps = new EnumMap<>(Language.class);
        for (Language language : Language.values()) {
            try (InputStream inputStream = getClass().getResourceAsStream("/" + language.getFileName())) {
                Properties properties = new Properties();
                properties.load(inputStream);

                Map<String, MessageFormat> map = new HashMap<>();
                properties.forEach((key, message) -> map.put((String) key, new MessageFormat((String) message)));

                languageMaps.put(language, map);
            } catch (IOException e) {
                Commons.getLogger().warning("Failed to load " + language.name().toUpperCase());
                e.printStackTrace();
            }
        }
        return languageMaps;
    }
}
