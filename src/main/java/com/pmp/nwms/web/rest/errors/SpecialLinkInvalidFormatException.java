package com.pmp.nwms.web.rest.errors;

public class SpecialLinkInvalidFormatException extends BadRequestAlertException {
    public SpecialLinkInvalidFormatException() {
        super(ErrorConstants.SPECIAL_LINK_INVALID_FORMAT_TYPE, "Invalid special link format.", "User", "user.invalid.special.link.format");
    }
}
