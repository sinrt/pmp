package com.pmp.nwms.web.rest.errors;

import java.util.Map;

public class MissingRequiredParamException extends BadRequestWithExtraInfoAlertException {
    public MissingRequiredParamException(String objectName, String paramName, Map<String, Object> extraInfo) {
        super(ErrorConstants.MISSING_REQUIRED_PARAM_TYPE, "Missing Required Param " + paramName + " from input " + objectName,
            objectName, "missingRequiredParam", extraInfo);
    }
}
