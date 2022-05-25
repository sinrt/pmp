package com.pmp.nwms.service.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.pmp.nwms.domain.enums.RecordingStorageStatus;
import com.pmp.nwms.util.jackson.RubruLongDateSerializer;

import java.io.Serializable;

public interface OpenClassroomRecordingInfoDTO extends Serializable {
    Long getId();

    String getRecordingId();

    String getRecordingName();

    @JsonSerialize(using = RubruLongDateSerializer.class)
    Long getStartTimeStamp();

    RecordingStorageStatus getStorageStatus();

    Long getClassroomId();
}
