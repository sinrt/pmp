package com.pmp.nwms.web.rest.errors;

import java.net.URI;
import java.util.Map;

public class BadRequestWithExtraInfoAlertException extends BadRequestAlertException {
    private Map<String, Object> extraInfo;

    public BadRequestWithExtraInfoAlertException(URI type, String defaultMessage, String entityName, String errorKey, Map<String, Object> extraInfo) {
        super(type, defaultMessage, entityName, errorKey);
        this.extraInfo = extraInfo;
    }

    public Map<String, Object> getExtraInfo() {
        return extraInfo;
    }
}
