package br.com.battlebits.commons.backend;

import br.com.battlebits.commons.translate.Language;

import java.text.MessageFormat;
import java.util.Map;

public interface DataTranslation {

    Map<Language, Map<String, MessageFormat>> loadTranslations();

    Class<? extends Enum> getEnum();
}
