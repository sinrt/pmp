package com.pmp.nwms.web.rest.errors;

public class InvalidSlideNumbersPeriodException extends BadRequestAlertException {
    public InvalidSlideNumbersPeriodException() {
        super(ErrorConstants.INVALID_SLIDE_NUMBERS_PERIOD_TYPE, "Invalid Slide Numbers Period", "classroomFileSlide", "invalid.slide.numbers.period");
    }
}
