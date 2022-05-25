package com.pmp.nwms.web.rest.errors;

public class ClassroomFileSlidingIsInProgressException extends BadRequestAlertException {
    public ClassroomFileSlidingIsInProgressException() {
        super(ErrorConstants.CLASSROOM_FILE_SLIDING_IS_IN_PROGRESS_TYPE, "ClassroomFileSlidingIsInProgress", "classroomFileSlide", "classroom.file.sliding.is.in.progress");
    }
}
