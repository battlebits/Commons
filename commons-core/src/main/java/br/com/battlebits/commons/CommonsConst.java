package br.com.battlebits.commons;

import br.com.battlebits.commons.translate.Language;

import java.time.LocalDate;

public class CommonsConst {

    public static Language DEFAULT_LANGUAGE = Language.PORTUGUESE;

    public final static long MULTIPLIER_DURATION = 60000 * 60;
    public final static String TRANSLATION_ID = "commons";
    public final static String FORUM_WEBSITE = "forum.battlebits.net";
    public final static String WEBSITE = "www.battlebits.com.br";
    public final static String SERVER_ADDRESS = "battlebits.com.br";
    public final static String STORE = "loja.battlebits.com.br";
    public final static String ADMIN_EMAIL = "admin@battlebits.com.br";
    public final static String TWITTER = "@BattlebitsMC";

    public static boolean isChristmas() {
        LocalDate now = LocalDate.now();
        return now.getMonthValue() == 12 && now.getDayOfMonth() == 25;
    }

}
