package com.pmp.nwms.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pmp.nwms.config.Constants;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A Course.
 */
@Entity
@Table(name = "course")
public class Course extends AbstractAuditingEntity<Long> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "title", length = 50, nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "teacher_pan")
    private boolean teacherPan;

    @NotNull
    @Column(name = "start_time", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_TIME_PATTERN, timezone = Constants.SYSTEM_TIME_ZONE)
    private Date startTime;

    @NotNull
    @Column(name = "finish_time", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_TIME_PATTERN, timezone = Constants.SYSTEM_TIME_ZONE)
    private Date finishTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties("courses")
    private User creator;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties("courses")
    private User modifier;

    @ManyToMany(fetch = FetchType.EAGER)
    @NotNull
    @JoinTable(name = "course_student",
        joinColumns = @JoinColumn(name = "course_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "students_id", referencedColumnName = "id"))
    private Set<User> students = new HashSet<>();


    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
    private Set<Classroom> classrooms = new HashSet<>();

    public Set<Classroom> getClassrooms() {
        return classrooms;
    }

    public void setClassrooms(Set<Classroom> classrooms) {
        this.classrooms = classrooms;
    }

    public Set<User> getStudents() {
        return students;
    }

    public void setStudents(Set<User> students) {
        this.students = students;
    }


    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Course title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public Course description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Course startTime(Date startTime) {
        this.startTime = startTime;
        return this;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public Course finishTime(Date finishTime) {
        this.finishTime = finishTime;
        return this;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public User getCreator() {
        return creator;
    }

    public Course creator(User user) {
        this.creator = user;
        return this;
    }

    public boolean isTeacherPan() {
        return teacherPan;
    }

    public void setTeacherPan(boolean teacherPan) {
        this.teacherPan = teacherPan;
    }

    public void setCreator(User user) {
        this.creator = user;
    }

    public User getModifier() {
        return modifier;
    }

    public Course modifier(User user) {
        this.modifier = user;
        return this;
    }

    public void setModifier(User user) {
        this.modifier = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Course course = (Course) o;
        if (course.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), course.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Course{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", startTime='" + getStartTime() + "'" +
            ", finishTime='" + getFinishTime() + "'" +
            "}";
    }

    @Override
    public Long fetchPrimaryKey() {
        return getId();
    }
}
