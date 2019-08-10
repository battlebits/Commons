package br.com.battlebits.commons.util.string;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringURLUtils {

    private static Pattern urlFinderPattern = Pattern
            .compile("((https?):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)", Pattern.CASE_INSENSITIVE);

    public static List<String> extractUrls(String text) {
        List<String> containedUrls = new ArrayList<String>();
        Matcher urlMatcher = urlFinderPattern.matcher(text);
        while (urlMatcher.find()) {
            containedUrls.add(urlMatcher.group(1));
        }
        return containedUrls;
    }

}
