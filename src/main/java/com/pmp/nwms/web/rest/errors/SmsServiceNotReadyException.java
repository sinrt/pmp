package com.pmp.nwms.web.rest.errors;

public class SmsServiceNotReadyException extends BadRequestAlertException {
    public SmsServiceNotReadyException() {
        super(ErrorConstants.SMS_SERVICE_NOT_READY_TYPE, "SmsSoapService is not ready to use!", "SmsSoapService", "SmsSoapService.not.ready");
    }
}
