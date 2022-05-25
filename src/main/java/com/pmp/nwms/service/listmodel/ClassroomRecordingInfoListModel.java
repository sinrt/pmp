package com.pmp.nwms.service.listmodel;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.pmp.nwms.config.Constants;
import com.pmp.nwms.domain.enums.RecordingStorageStatus;
import com.pmp.nwms.util.jackson.RubruLongDateSerializer;

import java.io.Serializable;
import java.util.Date;

public interface ClassroomRecordingInfoListModel extends Serializable {
    Long getId();

    @JsonSerialize(using = RubruLongDateSerializer.class)
    Long getStartTime();

    @JsonSerialize(using = RubruLongDateSerializer.class)
    Long getStartTimeStamp();

    @JsonSerialize(using = RubruLongDateSerializer.class)
    Long getFinalTimeStamp();

    Double getDuration();

    Long getFileSize();

    String getRecordingId();

    String getRecordingName();

    Long getRubruSessionId();

    String getRubruSessionName();

    String getStatus();

    RecordingStorageStatus getStorageStatus();

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_TIME_PATTERN, timezone = Constants.SYSTEM_TIME_ZONE)
    Date getStoreDateTime();
}
