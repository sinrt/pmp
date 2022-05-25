package com.pmp.nwms.domain;

import com.pmp.nwms.domain.enums.ClassroomStudentAuthority;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "classroom_students", indexes = {
    @Index(name = "idx_classroom_student_token", columnList = "token, classroom_id")
}
)
@AssociationOverrides({
    @AssociationOverride(name = "pk.classroom", joinColumns = @JoinColumn(name = "classroom_id")),
    @AssociationOverride(name = "pk.user", joinColumns = @JoinColumn(name = "students_id"))})
public class ClassroomStudent extends AbstractAuditingEntity<String>{

    private ClassroomStudentId pk = new ClassroomStudentId();
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


    @Column(name = "dynamic_student", nullable = false)
    private Boolean dynamicStudent = false;





    @EmbeddedId
    public ClassroomStudentId getPk() {
        return pk;
    }


    public String getAuthorityName() {
        return authorityName;
    }

    @Transient
    public User getStudent() {
        return this.getPk().getStudent();
    }

    public void setStudent(User student) {
        this.getPk().setStudent(student);
    }

    @Transient
    public Classroom getClassroom() {
        return this.getPk().getClassroom();
    }

    public void setClassroom(Classroom classroom) {
        this.getPk().setClassroom(classroom);
    }

    public void setPk(ClassroomStudentId pk) {
        this.pk = pk;
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
        if(ClassroomStudentAuthority.MODERATOR.name().equals(authorityName)){
            return true;
        }
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

    public Boolean getDynamicStudent() {
        if(dynamicStudent == null){
            dynamicStudent = false;
        }
        return dynamicStudent;
    }

    public void setDynamicStudent(Boolean dynamicStudent) {
        this.dynamicStudent = dynamicStudent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClassroomStudent that = (ClassroomStudent) o;
        return Objects.equals(pk, that.pk) &&
            Objects.equals(authorityName, that.authorityName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pk, authorityName);
    }

    @Override
    public String fetchPrimaryKey() {
        return pk.getClassroom().getId() + ":" + pk.getStudent().getId();
    }
}
