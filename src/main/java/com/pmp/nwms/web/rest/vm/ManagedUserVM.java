package com.pmp.nwms.web.rest.vm;

import com.pmp.nwms.config.Constants;
import com.pmp.nwms.logging.IgnoreLoggingValue;
import com.pmp.nwms.service.dto.UserDTO;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * View Model extending the UserDTO, which is meant to be used in the user management UI.
 */
public class ManagedUserVM extends UserDTO {


    @Size(min = Constants.PASSWORD_MIN_LENGTH, max = Constants.PASSWORD_MAX_LENGTH)
    @IgnoreLoggingValue
    private String password;

    @NotNull
    private String captcha;

    @NotNull
    private String clientKey;


    public ManagedUserVM() {
        // Empty constructor needed for Jackson.
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getCaptcha() {
        return captcha;
    }

    @Override
    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public String getClientKey() {
        return clientKey;
    }

    public void setClientKey(String clientKey) {
        this.clientKey = clientKey;
    }

    @Override
    public String toString() {
        return "ManagedUserVM{" +
            "} " + super.toString();
    }
}
