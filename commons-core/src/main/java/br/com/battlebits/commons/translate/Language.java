package br.com.battlebits.commons.translate;

public enum Language {

    PORTUGUESE;

    public String tl(String tag, Object... format) {
        return TranslationCommon.tl(this, tag, format);
    }
}
