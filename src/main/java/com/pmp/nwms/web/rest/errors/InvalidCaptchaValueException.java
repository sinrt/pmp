package com.pmp.nwms.web.rest.errors;

import java.util.Map;

public class InvalidCaptchaValueException extends BadRequestWithExtraInfoAlertException {
    public InvalidCaptchaValueException(Map<String, Object> extraInfo) {
        super(ErrorConstants.INVALID_CAPTCHA_VALUE_TYPE, "Invalid Captcha Value", "InvalidCaptchaValue", "InvalidCaptchaValue", extraInfo);
    }
}
