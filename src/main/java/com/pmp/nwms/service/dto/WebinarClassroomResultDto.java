package com.pmp.nwms.service.dto;

import java.io.Serializable;

public class WebinarClassroomResultDto implements Serializable {
    private Long classroomId;
    private String sessionUuidName;

    public WebinarClassroomResultDto(Long classroomId, String sessionUuidName) {
        this.classroomId = classroomId;
        this.sessionUuidName = sessionUuidName;
    }

    public Long getClassroomId() {
        return classroomId;
    }

    public void setClassroomId(Long classroomId) {
        this.classroomId = classroomId;
    }

    public String getSessionUuidName() {
        return sessionUuidName;
    }

    public void setSessionUuidName(String sessionUuidName) {
        this.sessionUuidName = sessionUuidName;
    }
}
