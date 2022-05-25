package com.pmp.nwms.web.rest.errors;

public class ClassroomFileSlidingStartedException extends BadRequestAlertException {
    public ClassroomFileSlidingStartedException() {
        super(ErrorConstants.CLASSROOM_FILE_SLIDING_STARTED_TYPE, "ClassroomFileSlidingStarted", "classroomFileSlide", "classroom.file.sliding.started");
    }
}
