package com.pmp.nwms.util;

import java.util.HashMap;
import java.util.Map;

public class StringUtil {

    private static final Map<String, String> CHARS = new HashMap<>();

    static {
        CHARS.put("٠", "0");
        CHARS.put("١", "1");
        CHARS.put("٢", "2");
        CHARS.put("٣", "3");
        CHARS.put("٤", "4");
        CHARS.put("٥", "5");
        CHARS.put("٦", "6");
        CHARS.put("٧", "7");
        CHARS.put("٨", "8");
        CHARS.put("٩", "9");
        CHARS.put("۰", "0");
        CHARS.put("۱", "1");
        CHARS.put("۲", "2");
        CHARS.put("۳", "3");
        CHARS.put("۴", "4");
        CHARS.put("۵", "5");
        CHARS.put("۶", "6");
        CHARS.put("۷", "7");
        CHARS.put("۸", "8");
        CHARS.put("۹", "9");
    }

    public static String convertPersianNumbers(String input){
        if(input == null || input.isEmpty()){
            return input;
        }
        String result = input;
        for (Map.Entry<String, String> entry : CHARS.entrySet()) {
            result = result.replace(entry.getKey(), entry.getValue());
        }
        return result;
    }
}
