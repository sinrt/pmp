package com.pmp.nwms.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumberUtil {
    public static final Pattern ALL_DIGIT_PATTERN = Pattern.compile("\\d+");

    public static boolean isAllDigits(String str) {
        Matcher matcher = ALL_DIGIT_PATTERN.matcher(str);
        return matcher.matches();
    }
}
