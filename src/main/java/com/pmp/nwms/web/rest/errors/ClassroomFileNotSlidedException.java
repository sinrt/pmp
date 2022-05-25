package com.pmp.nwms.web.rest.errors;

public class ClassroomFileNotSlidedException extends BadRequestAlertException {
    public ClassroomFileNotSlidedException() {
        super(ErrorConstants.CLASSROOM_FILE_NOT_SLIDED_TYPE, "Classroom File Not Slided", "classroomFileSlide", "classroom.file.not.slided");
    }
}
