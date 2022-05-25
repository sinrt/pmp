package com.pmp.nwms.web.rest.errors;

import com.pmp.nwms.domain.enums.RecordingStorageStatus;

public class UnableToDownloadClassroomRecordingDueToRecordingStorageStatusException  extends BadRequestAlertException{
    public UnableToDownloadClassroomRecordingDueToRecordingStorageStatusException(RecordingStorageStatus storageStatus) {
        super(ErrorConstants.UNABLE_TO_DOWNLOAD_CLASSROOM_RECORDING_DUE_TO_RECORDING_STORAGE_STATUS, "Classroom Recording Storage Status is " + storageStatus +", and not available to be downloaded", "ClassroomRecordingInfo", "unableToDownload");
    }
}
