package br.com.battlebits.commons.backend.properties;

import br.com.battlebits.commons.backend.DataTranslation;
import br.com.battlebits.commons.translate.Language;

import java.io.*;
import java.text.MessageFormat;
import java.util.*;

public class PropertiesStorageDataTranslation implements DataTranslation {

    private File dirLocation;
    private Class<? extends Enum> translateTags;

    public PropertiesStorageDataTranslation(File dirLocation, Class<? extends Enum> translateTags) {
        this.dirLocation = dirLocation;
        this.translateTags = translateTags;
    }

    @Override
    public Map<Language, Map<String, MessageFormat>> loadTranslations() {
        Map<Language, Map<String, MessageFormat>> languageMaps = new HashMap<>();
        for (Language language : Language.values()) {
            File file = new File(dirLocation, language.getFileName());
            try (InputStream inputStream = new FileInputStream(file)) {
                SortedProperties properties = new SortedProperties();
                properties.load(inputStream);

                Map<String, MessageFormat> map = new HashMap<>();
                properties.forEach((key, message) -> map.put(String.valueOf(key), new MessageFormat((String) message)));

                boolean needUpdate = false;
                for (Enum enumConstant : translateTags.getEnumConstants()) {
                    if (!properties.containsKey(enumConstant.toString())) {
                        properties.setProperty(enumConstant.toString(), "");
                        needUpdate = true;
                    }
                }
                if (needUpdate) {
                    OutputStream outputStream = new FileOutputStream(file);
                    properties.store(outputStream, null);
                }
                languageMaps.put(language, map);
                System.out.println("Successfully load " + language.name().toUpperCase());
            } catch (IOException e) {
//                Commons.getLogger().warning("Failed to load " + language.name().toUpperCase());
                e.printStackTrace();
            }
        }
        return languageMaps;
    }

    @Override
    public Class<? extends Enum> getEnum() {
        return translateTags;
    }

    class SortedProperties extends Properties {
        public Enumeration keys() {
            Enumeration keysEnum = super.keys();
            Vector<String> keyList = new Vector<String>();
            while (keysEnum.hasMoreElements()) {
                keyList.add((String) keysEnum.nextElement());
            }
            Collections.sort(keyList);
            return keyList.elements();
        }

    }
}
