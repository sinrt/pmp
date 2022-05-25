package com.pmp.nwms.web.rest.errors;

import com.payamak.panel.client.SendSimpleSMS2ResultCodes;

public class PayamakSmsSendException extends RuntimeException {
    public PayamakSmsSendException(SendSimpleSMS2ResultCodes resultCode) {
        //todo ? handle this exception properly
        super(resultCode.name() + " : " + resultCode.getCode());
    }
}
