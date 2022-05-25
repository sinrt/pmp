package com.pmp.nwms.model;

import java.io.Serializable;
import java.time.Instant;

public interface ClassroomStudentModel extends Serializable {

    Long getClassroomId();

    Long getStudentId();

    String getAuthorityName();

    String getFullName();

    boolean isNeedsLogin();

    String getToken();

    Integer getMaxUseCount();

    String getCreatedBy();

    Instant getCreatedDate();

    String getLastModifiedBy();

    Instant getLastModifiedDate();

    Long getVersion();
}
