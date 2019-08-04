package br.com.battlebits.commons.translate;

public enum Language {

    PORTUGUESE("pt_br.properties");

    private String fileName;

    Language(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public String tl(String tag, Object... format) {
        return TranslationCommon.tl(this, tag, format);
    }
}
