package br.com.battlebits.commons.backend.properties;

import br.com.battlebits.commons.backend.DataTranslation;
import br.com.battlebits.commons.translate.Language;

import java.io.*;
import java.text.MessageFormat;
import java.util.*;

public class PropertiesStorageDataTranslation implements DataTranslation {

    private Class<? extends Enum> translateTags;

    public PropertiesStorageDataTranslation(Class<? extends Enum> translateTags) {
        this.translateTags = translateTags;
    }

    @Override
    public Map<Language, Map<String, MessageFormat>> loadTranslations() {
        Map<Language, Map<String, MessageFormat>> languageMaps = new HashMap<>();
        for (Language language : Language.values()) {
            try (InputStream inputStream = getClass().getResourceAsStream("/"+ language.getFileName())) {
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
                    PrintWriter writer =
                            new PrintWriter(new File(getClass().getResource("/"+ language.getFileName()).getPath()));
                    properties.store(writer, null);
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
