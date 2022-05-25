package com.pmp.nwms.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ClassroomStudentId implements Serializable {

    private Classroom classroom;
    private User student;

    public ClassroomStudentId() {
    }

    public ClassroomStudentId(Classroom classroom, User student) {
        this.classroom = classroom;
        this.student = student;
    }


    @JsonIgnore
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    public Classroom getClassroom() {
        return classroom;
    }

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    public User getStudent() {
        return student;
    }

    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }

    public void setStudent(User student) {
        this.student = student;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClassroomStudentId that = (ClassroomStudentId) o;
        return Objects.equals(classroom, that.classroom) &&
            Objects.equals(student, that.student);
    }

    @Override
    public int hashCode() {
        return Objects.hash(classroom.getId(), student.getId());
    }

    @Override
    public String toString() {
        return "ClassroomStudentId{" +
            "classroom=" + classroom.getId() +
            ", student=" + student.getId() +
            '}';
    }
}
