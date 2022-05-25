package com.pmp.nwms.web.rest.vm;

import java.io.Serializable;

public interface ClassroomStudentInfoVM extends Serializable {
    String getLogin();

    String getFirstName();

    String getLastName();

    String getAuthorityName();

    boolean isNeedsLogin();

    Integer getMaxUseCount();

    String getSessionName();

    String getSessionUuidName();

    String getToken();

    Boolean getUseEnterToken();

    boolean isTeacherPan();
}
