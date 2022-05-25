package com.pmp.nwms.service.dto;

import java.io.Serializable;

public interface ClassroomStudentDTO extends Serializable {
    Long getClassroomId();

    String  getClassroomName();

    String  getClassroomSessionUuidName();

    String getAuthorityName();

    boolean isNeedsLogin();

    Integer getMaxUseCount();

    String getToken();

    Long getCreatorId();
}
