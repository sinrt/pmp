package com.pmp.nwms.domain.ids;

import java.io.Serializable;

public class DeletedCourseStudentId implements Serializable {
    private Long userId;
    private Long courseId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }
}
