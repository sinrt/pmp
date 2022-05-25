package com.pmp.nwms.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

import java.util.Locale;

public class MessageUtil {
    private static final Logger log = LoggerFactory.getLogger(MessageUtil.class);

    public static String getMessage(MessageSource messageSource, String key, Object[] args, Locale locale) {
        try {
            return messageSource.getMessage(key, args, locale);
        } catch (NoSuchMessageException e) {
            log.warn("Key:" + key, e);
            return "??" + key + "??";
        }
    }
}
