package com.pmp.nwms.service.dto;
import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the SignalServerMeetingLog entity.
 */
public class SignalServerMeetingLogDTO implements Serializable {

    private Long id;

    private String sessionName;

    private Long duration;

    private Long creatorID;

    private String creatorUserName;

    private String creatorName;

    private String creatorFamily;

    private ZonedDateTime sessionStartDate;

    private ZonedDateTime sessionFinishDate;

    private String reason;

    private String signalServerLogIds;

    private Boolean incompleteStatus;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSessionName() {
        return sessionName;
    }

    public void setSessionName(String sessionName) {
        this.sessionName = sessionName;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Long getCreatorID() {
        return creatorID;
    }

    public void setCreatorID(Long creatorID) {
        this.creatorID = creatorID;
    }

    public String getCreatorUserName() {
        return creatorUserName;
    }

    public void setCreatorUserName(String creatorUserName) {
        this.creatorUserName = creatorUserName;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getCreatorFamily() {
        return creatorFamily;
    }

    public void setCreatorFamily(String creatorFamily) {
        this.creatorFamily = creatorFamily;
    }

    public ZonedDateTime getSessionStartDate() {
        return sessionStartDate;
    }

    public void setSessionStartDate(ZonedDateTime sessionStartDate) {
        this.sessionStartDate = sessionStartDate;
    }

    public ZonedDateTime getSessionFinishDate() {
        return sessionFinishDate;
    }

    public void setSessionFinishDate(ZonedDateTime sessionFinishDate) {
        this.sessionFinishDate = sessionFinishDate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getSignalServerLogIds() {
        return signalServerLogIds;
    }

    public void setSignalServerLogIds(String signalServerLogIds) {
        this.signalServerLogIds = signalServerLogIds;
    }

    public Boolean getIncompleteStatus() {
        return incompleteStatus;
    }

    public void setIncompleteStatus(Boolean incompleteStatus) {
        this.incompleteStatus = incompleteStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SignalServerMeetingLogDTO signalServerMeetingLogDTO = (SignalServerMeetingLogDTO) o;
        if (signalServerMeetingLogDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), signalServerMeetingLogDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SignalServerMeetingLogDTO{" +
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
}
