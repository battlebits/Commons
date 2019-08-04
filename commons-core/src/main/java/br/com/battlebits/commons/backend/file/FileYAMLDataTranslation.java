package br.com.battlebits.commons.backend.file;

import br.com.battlebits.commons.backend.DataTranslation;
import br.com.battlebits.commons.translate.Language;

import java.text.MessageFormat;
import java.util.Map;

public class FileYAMLDataTranslation implements DataTranslation {

    private FileYAMLDatabase database;

    public FileYAMLDataTranslation(FileYAMLDatabase database) {
        this.database = database;
    }

    @Override
    public void setup(String dirLocation) {

    }

    @Override
    public Map<Language, Map<String, MessageFormat>> loadTranslations() {
        return null;
    }

    @Override
    public Map<String, MessageFormat> loadTranslation(Language lang) {
        return null;
    }
}
