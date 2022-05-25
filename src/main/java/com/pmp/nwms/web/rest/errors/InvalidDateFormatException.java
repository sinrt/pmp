package com.pmp.nwms.web.rest.errors;

public class InvalidDateFormatException extends BadRequestAlertException {
    public InvalidDateFormatException(String filedName, String fieldValue, String expectedPattern) {
        super(ErrorConstants.INVALID_DATE_FORMAT_TYPE, filedName + " with value " + fieldValue + " expected to be formatted like " + expectedPattern, "InvalidDateFormat", "InvalidDateFormat");
    }
}
