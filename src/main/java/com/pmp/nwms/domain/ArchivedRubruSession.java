package com.pmp.nwms.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pmp.nwms.config.Constants;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "arc_rubru_session")
public class ArchivedRubruSession implements BaseEntity<Long> {
    @Id
    private Long id;
    @Basic
    @Column(name = "session_id", length = 50, nullable = false)
    private String sessionId;
    @Basic
    @Column(name = "start_date", nullable = false)
    private Date startDate;
    @Basic
    @Column(name = "end_date", nullable = true)
    private Date endDate;
    @Basic
    @Column(name = "duration", nullable = true)
    private Long duration;
    @Basic
    @Column(name = "end_reason", length = 255, nullable = true)
    private String endReason;
    @Basic
    @Column(name = "classroom_id", nullable = true)
    private Long classroomId;
    @Basic
    @Column(name = "creator_id", nullable = true)
    private Long creatorId;
    @Basic
    @Column(name = "purchase_status_id ", nullable = true)
    private Long purchaseStatusId;
    @Basic
    @Column(name = "ovws_url", length = 255, nullable = true)
    private String ovwsUrl;
    @Basic
    @Column(name = "app_url", length = 255, nullable = true)
    private String appUrl;

    @Basic
    @Column(name = "archive_date", nullable = false)
    private Date archiveDate;

    public ArchivedRubruSession() {
    }
    public ArchivedRubruSession(RubruSession rubruSession) {
        this.id = rubruSession.getId();
        this.sessionId = rubruSession.getSessionId();
        this.startDate = rubruSession.getStartDate();
        this.endDate = rubruSession.getEndDate();
        this.duration = rubruSession.getDuration();
        this.endReason = rubruSession.getEndReason();
        this.classroomId = rubruSession.getClassroomId();
        this.creatorId = rubruSession.getCreatorId();
        this.purchaseStatusId = rubruSession.getPurchaseStatusId();
        this.ovwsUrl = rubruSession.getOvwsUrl();
        this.appUrl = rubruSession.getAppUrl();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public String getEndReason() {
        return endReason;
    }

    public void setEndReason(String endReason) {
        this.endReason = endReason;
    }


    public Long getClassroomId() {
        return classroomId;
    }

    public void setClassroomId(Long classroomId) {
        this.classroomId = classroomId;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public Long getPurchaseStatusId() {
        return purchaseStatusId;
    }

    public void setPurchaseStatusId(Long purchaseStatusId) {
        this.purchaseStatusId = purchaseStatusId;
    }

    public String getOvwsUrl() {
        return ovwsUrl;
    }

    public void setOvwsUrl(String ovwsUrl) {
        this.ovwsUrl = ovwsUrl;
    }

    public String getAppUrl() {
        return appUrl;
    }

    public void setAppUrl(String appUrl) {
        this.appUrl = appUrl;
    }

    public Date getArchiveDate() {
        return archiveDate;
    }

    public void setArchiveDate(Date archiveDate) {
        this.archiveDate = archiveDate;
    }

    @Override
    public Long fetchPrimaryKey() {
        return getId();
    }
}
