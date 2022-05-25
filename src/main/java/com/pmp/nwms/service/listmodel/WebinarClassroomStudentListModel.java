package com.pmp.nwms.service.listmodel;

import java.io.Serializable;

public interface WebinarClassroomStudentListModel  extends Serializable {

    Long getUserId();
    Long getClassroomId();
    String getLogin();
    String getFirstName();
    String getLastName();
    String getAuthorityName();
    boolean isNeedsLogin();
    Integer getMaxUseCount();
    String getToken();
    Boolean getDynamicStudent();
}
