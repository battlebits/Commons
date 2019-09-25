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
    private Class<? extends Enum> translateTags;

    public PropertiesStorageDataTranslation(File dirLocation, Class<? extends Enum> translateTags) {
        this.dirLocation = dirLocation;
        this.translateTags = translateTags;
    }

    @Override
    public EnumMap<Language, Map<Enum<?>, MessageFormat>> loadTranslations() {
        EnumMap<Language, Map<Enum<?>, MessageFormat>> languageMaps = new EnumMap<>(Language.class);
        for (Language language : Language.values()) {
            try (InputStream inputStream = new FileInputStream(new File(dirLocation, language.getFileName()))) {
                Properties properties = new Properties();
                properties.load(inputStream);

                Map<Enum<?>, MessageFormat> map = new HashMap<>();
                properties.forEach((key, message) -> map.put(Enum.valueOf(translateTags, String.valueOf(key)), new MessageFormat((String) message)));

                languageMaps.put(language, map);
            } catch (IOException e) {
                Commons.getLogger().warning("Failed to load " + language.name().toUpperCase());
                e.printStackTrace();
            }
        }
        return languageMaps;
    }
}
