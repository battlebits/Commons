package br.com.battlebits.commons.backend.properties;

import br.com.battlebits.commons.Commons;
import br.com.battlebits.commons.backend.DataTranslation;
import br.com.battlebits.commons.translate.Language;

import java.io.*;
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
            File file = new File(dirLocation, language.getFileName());
            try (InputStream inputStream = new FileInputStream(file)) {
                Properties properties = new Properties();
                properties.load(inputStream);

                Map<Enum<?>, MessageFormat> map = new HashMap<>();
                properties.forEach((key, message) -> map.put(Enum.valueOf(translateTags, String.valueOf(key).toUpperCase()
                        .replace("-", "_").replace(".", "_")), new MessageFormat((String) message)));

                boolean needUpdate = false;
                for (Enum enumConstant : translateTags.getEnumConstants()) {
                    if(!properties.containsKey(enumConstant.toString())) {
                        properties.setProperty(enumConstant.toString(), "");
                        needUpdate = true;
                    }
                }
                if(needUpdate) {
                    OutputStream outputStream = new FileOutputStream(file);
                    properties.store(outputStream, null);
                }
                languageMaps.put(language, map);
            } catch (IOException e) {
                Commons.getLogger().warning("Failed to load " + language.name().toUpperCase());
                e.printStackTrace();
            }
        }
        return languageMaps;
    }

    @Override
    public Class<? extends Enum> getEnum() {
        return translateTags;
    }
}
