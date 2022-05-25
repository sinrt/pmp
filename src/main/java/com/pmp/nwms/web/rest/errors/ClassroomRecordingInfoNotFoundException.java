package com.pmp.nwms.web.rest.errors;

public class ClassroomRecordingInfoNotFoundException extends BadRequestAlertException {
    public ClassroomRecordingInfoNotFoundException(Long classroomId, String recordingId, String downloadBaseUrl) {
        super(ErrorConstants.CLASSROOM_RECORDING_INFO_NOT_FOUND_TYPE, "ClassroomRecordingInfoNotFoundException classroomId : [" + classroomId + "], recordingId : [" + recordingId + "], downloadBaseUrl : [" + downloadBaseUrl + "]", "ClassroomRecordingInfoNotFound", "ClassroomRecordingInfoNotFound");
    }
}
