package com.pmp.nwms.web.rest.errors;

public class UnableToChangeFieldValueException extends BadRequestAlertException {
    public UnableToChangeFieldValueException(String entityName, String fieldName) {
        super(ErrorConstants.UNABLE_TO_CHANGE_FIELD_VALUE_TYPE, "Can not change the value of " + fieldName + " in " + entityName, entityName, "unableToChangeFieldValue");
    }
}
