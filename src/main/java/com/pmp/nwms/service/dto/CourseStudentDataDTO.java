package com.pmp.nwms.service.dto;

import java.io.Serializable;

public interface CourseStudentDataDTO extends Serializable {
    Long getUserId();

    String getLogin();

    String getFirstName();

    String getLastName();
}
