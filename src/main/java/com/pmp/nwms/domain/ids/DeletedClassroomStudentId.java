package com.pmp.nwms.domain.ids;

import java.io.Serializable;

public class DeletedClassroomStudentId implements Serializable {
    private Long classroomId;
    private Long studentId;

    public Long getClassroomId() {
        return classroomId;
    }

    public void setClassroomId(Long classroomId) {
        this.classroomId = classroomId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }
}
