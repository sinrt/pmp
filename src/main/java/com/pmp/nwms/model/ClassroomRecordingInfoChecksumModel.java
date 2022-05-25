package com.pmp.nwms.model;

import java.io.Serializable;

public interface ClassroomRecordingInfoChecksumModel extends Serializable {
    Long getId();

    Double getDuration();

    Long getFileSize();

    Long getClassroomCreatorId();

    default String toDataString() {
        return "uid=" + getClassroomCreatorId() + "&fid=" + getId() + "&du=" + getDuration() + "&fs=" + getFileSize();
    }
}
