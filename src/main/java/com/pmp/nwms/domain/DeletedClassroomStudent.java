package com.pmp.nwms.domain;

import com.pmp.nwms.domain.ids.DeletedClassroomStudentId;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "del_classroom_students")
@IdClass(DeletedClassroomStudentId.class)
public class DeletedClassroomStudent extends AbstractDeletedEntity<String> {

    @Id
    @Column(name = "classroom_id")
    private Long classroomId;
    @Id
    @Column(name = "student_id")
    private Long studentId;


    @Column(name = "authority_name", nullable = true, length = 50)
    private String authorityName;

    @Column(name = "full_name", nullable = true, length = 100)
    private String fullName;

    @Column(name = "needs_login")
    private boolean needsLogin = false;

    @Column(name = "token", length = 200)
    private String token;

    @Column(name = "max_use_count")
    private Integer maxUseCount;


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

    public String getAuthorityName() {
        return authorityName;
    }

    public void setAuthorityName(String authorityName) {
        this.authorityName = authorityName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public boolean isNeedsLogin() {
        return needsLogin;
    }

    public void setNeedsLogin(boolean needsLogin) {
        this.needsLogin = needsLogin;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getMaxUseCount() {
        return maxUseCount;
    }

    public void setMaxUseCount(Integer maxUseCount) {
        this.maxUseCount = maxUseCount;
    }

    @Override
    public String fetchPrimaryKey() {
        return classroomId + ":" + studentId;
    }
}
