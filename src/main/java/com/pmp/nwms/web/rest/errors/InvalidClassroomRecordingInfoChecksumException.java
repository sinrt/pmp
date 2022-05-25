package com.pmp.nwms.web.rest.errors;

public class InvalidClassroomRecordingInfoChecksumException extends BadRequestAlertException {
    public InvalidClassroomRecordingInfoChecksumException() {
        super(ErrorConstants.INVALID_CLASSROOM_RECORDING_INFO_CHECKSUM_TYPE, "InvalidClassroomRecordingInfoChecksum.", "ClassroomRecordingInfo", "InvalidClassroomRecordingInfoChecksum");
    }
}
