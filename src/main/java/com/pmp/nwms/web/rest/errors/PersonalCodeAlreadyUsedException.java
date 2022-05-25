package com.pmp.nwms.web.rest.errors;

public class PersonalCodeAlreadyUsedException extends BadRequestAlertException {
    private static final long serialVersionUID = 1L;

    public PersonalCodeAlreadyUsedException() {
        super(ErrorConstants.PERSONALCODE_ALREADY_USED_TYPE, "PersonalCode is already in use!", "userManagement", "personalcodeexists");
    }
}
