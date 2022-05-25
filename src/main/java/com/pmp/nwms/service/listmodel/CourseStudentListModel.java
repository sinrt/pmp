package com.pmp.nwms.service.listmodel;

import java.io.Serializable;

public interface CourseStudentListModel extends Serializable {
    Long getUserId();

    String getLogin();

    String getFirstName();

    String getLastName();

}
