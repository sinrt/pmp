package com.pmp.nwms.service.so;

import javax.validation.constraints.NotNull;

public class ClassroomFileSo extends BaseSearchObject {

    @NotNull
    private Long classroomId;

    public Long getClassroomId() {
        return classroomId;
    }

    public void setClassroomId(Long classroomId) {
        this.classroomId = classroomId;
    }

    @Override
    public boolean isClean() {
        return classroomId != null;
    }
}
