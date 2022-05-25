package com.pmp.nwms.service.listmodel;

import java.io.Serializable;

public interface CourseListModel extends Serializable {
    Long getId();

    String getTitle();

    String getDescription();

    boolean isTeacherPan();

}
