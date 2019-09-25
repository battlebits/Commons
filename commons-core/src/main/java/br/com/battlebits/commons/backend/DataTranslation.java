package br.com.battlebits.commons.backend;

import br.com.battlebits.commons.translate.Language;

import java.text.MessageFormat;
import java.util.EnumMap;
import java.util.Map;

public interface DataTranslation {

    EnumMap<Language, Map<Enum<?>, MessageFormat>> loadTranslations();

    Class<? extends Enum> getEnum();
}
