package com.pmp.nwms.web.rest.errors;

import org.springframework.security.core.AuthenticationException;
import org.zalando.problem.Status;

public class RestAuthenticationException extends AuthenticationException {

    private static final long serialVersionUID = 1L;

    private boolean sendCaptcha;

    public RestAuthenticationException(boolean sendCaptcha) {
        super("Incorrect username or password");
        this.sendCaptcha = sendCaptcha;
    }

    public boolean isSendCaptcha() {
        return sendCaptcha;
    }
}
