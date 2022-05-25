package com.pmp.nwms.web.rest.errors;

public class UnsupportedFileTypeForSlidingException extends BadRequestAlertException {
    public UnsupportedFileTypeForSlidingException() {
        super(ErrorConstants.UNSUPPORTED_FILE_TYPE_FOR_SLIDING_TYPE, "Unsupported File Type For Sliding", "classroomFileSlide", "unsupported.file.type.for.sliding");
    }
}
