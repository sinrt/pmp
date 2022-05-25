package com.pmp.nwms.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;


/**
 * A Deleted Course.
 */
@Entity
@Table(name = "del_course")
public class DeletedCourse extends AbstractDeletedEntity<Long> {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @Column(name = "title", length = 50, nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "teacher_pan")
    private boolean teacherPan;

    @Column(name = "start_time", nullable = false)
    private ZonedDateTime startTime;

    @Column(name = "finish_time", nullable = false)
    private ZonedDateTime finishTime;

    @Column(name = "creator_id")
    private Long creatorId;

    @Column(name = "modifier_id")
    private Long modifierId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isTeacherPan() {
        return teacherPan;
    }

    public void setTeacherPan(boolean teacherPan) {
        this.teacherPan = teacherPan;
    }

    public ZonedDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(ZonedDateTime startTime) {
        this.startTime = startTime;
    }

    public ZonedDateTime getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(ZonedDateTime finishTime) {
        this.finishTime = finishTime;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public Long getModifierId() {
        return modifierId;
    }

    public void setModifierId(Long modifierId) {
        this.modifierId = modifierId;
    }

    @Override
    public Long fetchPrimaryKey() {
        return getId();
    }
}
