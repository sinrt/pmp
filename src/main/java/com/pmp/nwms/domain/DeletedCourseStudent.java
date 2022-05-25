package com.pmp.nwms.domain;


import com.pmp.nwms.domain.ids.DeletedCourseStudentId;

import javax.persistence.*;
import java.io.Serializable;

/**
 * A Course.
 */
@Entity
@Table(name = "del_course_student")
@IdClass(DeletedCourseStudentId.class)
public class DeletedCourseStudent implements Serializable, BaseEntity<String> {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "user_id")
    private Long userId;
    @Id
    @Column(name = "course_id")
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

    @Override
    public String fetchPrimaryKey() {
        return courseId + ":" + userId;
    }
}
