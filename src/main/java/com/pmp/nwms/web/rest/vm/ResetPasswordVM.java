package com.pmp.nwms.web.rest.vm;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class ResetPasswordVM implements Serializable {
    @NotNull
    private String email;
    @NotNull
    private String captcha;
    @NotNull
    private String clientKey;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public String getClientKey() {
        return clientKey;
    }

    public void setClientKey(String clientKey) {
        this.clientKey = clientKey;
    }
}
