package br.com.battlebits.commons.translate;

import br.com.battlebits.commons.account.Group;

import java.util.Optional;

public enum Language {

    PORTUGUESE("pt_br.properties");

    private String fileName;

    Language(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public String tl(Enum<?> tag, Object... format) {
        return TranslationCommon.tl(this, tag, format);
    }

    public static Optional<Language> byId(int id) {
        for (Language language : values()) {
            if(language.ordinal() == id) {
                return Optional.of(language);
            }
        }
        return Optional.empty();
    }

}
