package com.pmp.nwms.domain.enums;

public enum RecordingStorageStatus {
    WaitForStop,
    WaitForFinalize,
    ReadyToDownload,
    Downloading,
    Downloaded,
    FinalizeFailed,
    Deleted,
}
