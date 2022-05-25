package com.pmp.nwms.config;

/**
 * Application constants.
 */
public final class Constants {

    // Regex for acceptable logins
    public static final String LOGIN_REGEX = "^[_.@A-Za-z0-9-]*$";
    public static final String SPECIAL_LINK_REGEX = "^[A-Za-z0-9\\-_]+$";
    public static final String CLASSROOM_NAME_REGEX = "^[آ-یa-z0-9\\-_]+$";
    public static final String CLASSROOM_NAME_WITH_SPACE_REGEX = "^[آ-یa-z0-9\\-_ ]+$";
    public static final String NOT_PARTICIPANT_NAME_REGEX = "[^ _a-zA-Z0-9\u0621-\u064a\u0660-\u09f9\u200C]+";
    public static final String PARTICIPANT_NAME_REGEX = "[ _a-zA-Z0-9\u0621-\u064a\u0660-\u09f9\u200C]+";

    public static final String SYSTEM_ACCOUNT = "system";
    public static final String ANONYMOUS_USER = "anonymoususer";
    public static final String DEFAULT_LANGUAGE = "fa";
    public static final String EN_LANGUAGE = "en";

    public static final int DEFAULT_PAGE_SIZE = 10;
    public static final int DEFAULT_FIRST_RESULT = 0;

    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String SYSTEM_TIME_ZONE = "Asia/Tehran";

    public static final int PASSWORD_MIN_LENGTH = 4;

    public static final int PASSWORD_MAX_LENGTH = 100;


    private Constants() {
    }
}
