package com.pmp.nwms.security;

/**
 * Constants for Spring Security authorities.
 */
public final class AuthoritiesConstants {

    public static final String ADMIN = "ROLE_ADMIN";

    public static final String USER = "ROLE_USER";

    public static final String ANONYMOUS = "ROLE_ANONYMOUS";

    public static final String OSTAD = "ROLE_OSTAD";

    public static final String SINA= "ROLE_SINA";

    public static final String TEACHER= "ROLE_TEACHER";

    public static final String ROLE_USER_B2C= "ROLE_USER_B2C";

    public static final String API= "ROLE_API";

    public static final String OUTER_MANAGER= "ROLE_OUTER_MANAGER";

    private AuthoritiesConstants() {
    }
}
