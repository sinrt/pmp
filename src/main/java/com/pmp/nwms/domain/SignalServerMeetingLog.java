package com.pmp.nwms.domain;



import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A SignalServerMeetingLog.
 */
@Entity
@Table(name = "signal_server_meeting_log")
public class SignalServerMeetingLog implements Serializable, BaseEntity<Long> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "session_name")
    private String sessionName;

    @Column(name = "duration")
    private Long duration;

    @Column(name = "creator_id")
    private Long creatorID;

    @Column(name = "creator_user_name")
    private String creatorUserName;

    @Column(name = "creator_name")
    private String creatorName;

    @Column(name = "creator_family")
    private String creatorFamily;

    @Column(name = "session_start_date")
    private ZonedDateTime sessionStartDate;

    @Column(name = "session_finish_date")
    private ZonedDateTime sessionFinishDate;

    @Column(name = "reason")
    private String reason;

    @Column(name = "signal_server_log_ids")
    private String signalServerLogIds;

    @Column(name = "incomplete_status")
    private Boolean incompleteStatus;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSessionName() {
        return sessionName;
    }

    public SignalServerMeetingLog sessionName(String sessionName) {
        this.sessionName = sessionName;
        return this;
    }

    public void setSessionName(String sessionName) {
        this.sessionName = sessionName;
    }

    public Long getDuration() {
        return duration;
    }

    public SignalServerMeetingLog duration(Long duration) {
        this.duration = duration;
        return this;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Long getCreatorID() {
        return creatorID;
    }

    public SignalServerMeetingLog creatorID(Long creatorID) {
        this.creatorID = creatorID;
        return this;
    }

    public void setCreatorID(Long creatorID) {
        this.creatorID = creatorID;
    }

    public String getCreatorUserName() {
        return creatorUserName;
    }

    public SignalServerMeetingLog creatorUserName(String creatorUserName) {
        this.creatorUserName = creatorUserName;
        return this;
    }

    public void setCreatorUserName(String creatorUserName) {
        this.creatorUserName = creatorUserName;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public SignalServerMeetingLog creatorName(String creatorName) {
        this.creatorName = creatorName;
        return this;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getCreatorFamily() {
        return creatorFamily;
    }

    public SignalServerMeetingLog creatorFamily(String creatorFamily) {
        this.creatorFamily = creatorFamily;
        return this;
    }

    public void setCreatorFamily(String creatorFamily) {
        this.creatorFamily = creatorFamily;
    }

    public ZonedDateTime getSessionStartDate() {
        return sessionStartDate;
    }

    public SignalServerMeetingLog sessionStartDate(ZonedDateTime sessionStartDate) {
        this.sessionStartDate = sessionStartDate;
        return this;
    }

    public void setSessionStartDate(ZonedDateTime sessionStartDate) {
        this.sessionStartDate = sessionStartDate;
    }

    public ZonedDateTime getSessionFinishDate() {
        return sessionFinishDate;
    }

    public SignalServerMeetingLog sessionFinishDate(ZonedDateTime sessionFinishDate) {
        this.sessionFinishDate = sessionFinishDate;
        return this;
    }

    public void setSessionFinishDate(ZonedDateTime sessionFinishDate) {
        this.sessionFinishDate = sessionFinishDate;
    }

    public String getReason() {
        return reason;
    }

    public SignalServerMeetingLog reason(String reason) {
        this.reason = reason;
        return this;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getSignalServerLogIds() {
        return signalServerLogIds;
    }

    public SignalServerMeetingLog signalServerLogIds(String signalServerLogIds) {
        this.signalServerLogIds = signalServerLogIds;
        return this;
    }

    public void setSignalServerLogIds(String signalServerLogIds) {
        this.signalServerLogIds = signalServerLogIds;
    }

    public Boolean getIncompleteStatus() {
        return incompleteStatus;
    }

    public SignalServerMeetingLog incompleteStatus(Boolean incompleteStatus) {
        this.incompleteStatus = incompleteStatus;
        return this;
    }

    public void setIncompleteStatus(Boolean incompleteStatus) {
        this.incompleteStatus = incompleteStatus;
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
        SignalServerMeetingLog signalServerMeetingLog = (SignalServerMeetingLog) o;
        if (signalServerMeetingLog.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), signalServerMeetingLog.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SignalServerMeetingLog{" +
            "id=" + getId() +
            ", sessionName='" + getSessionName() + "'" +
            ", duration=" + getDuration() +
            ", creatorID=" + getCreatorID() +
            ", creatorUserName='" + getCreatorUserName() + "'" +
            ", creatorName='" + getCreatorName() + "'" +
            ", creatorFamily='" + getCreatorFamily() + "'" +
            ", sessionStartDate='" + getSessionStartDate() + "'" +
            ", sessionFinishDate='" + getSessionFinishDate() + "'" +
            ", reason='" + getReason() + "'" +
            ", signalServerLogIds='" + getSignalServerLogIds() + "'" +
            ", incompleteStatus='" + getIncompleteStatus() + "'" +
            "}";
    }

    @Override
    public Long fetchPrimaryKey() {
        return getId();
    }
}
