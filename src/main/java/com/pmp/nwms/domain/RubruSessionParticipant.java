package com.pmp.nwms.domain;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "rubru_session_participant",
indexes = {
@Index(name = "idx_session_participant_token", columnList = "user_token")
})
public class RubruSessionParticipant implements BaseEntity<Long>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "participant_id", length = 255, nullable = false)
    private String participantId;
    @Basic
    @Column(name = "location", length = 100, nullable = true)
    private String location;
    @Basic
    @Column(name = "platform", length = 255, nullable = true)
    private String platform;
    @Basic
    @Column(name = "participant_name", length = 255, nullable = false)
    private String participantName;
    @Basic
    @Column(name = "user_token", length = 200, nullable = true)
    private String userToken;
    @Basic
    @Column(name = "participant_type", length = 20, nullable = true)
    private String participantType;
    @Basic
    @Column(name = "serverData", length = 255, nullable = true)
    private String serverData;
    @Basic
    @Column(name = "join_date_time", nullable = false)
    private Date joinDateTime;
    @Basic
    @Column(name = "leave_date_time", nullable = true)
    private Date leaveDateTime;
    @Basic
    @Column(name = "duration", nullable = true)
    private Long duration;
    @Basic
    @Column(name = "leave_reason", length = 255, nullable = true)
    private String leaveReason;

    @Basic
    @Column(name = "user_id", nullable = true)
    private Long userId;


    @Basic
    @Column(name = "client_id", length = 50)
    private String clientId;

    @Basic
    @Column(name = "participant_key", length = 50)
    private String participantKey;

    @Basic
    @Column(name = "participant_role", length = 50)
    private String participantRole;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "rubru_session_id")
    private RubruSession rubruSession;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getParticipantId() {
        return participantId;
    }

    public void setParticipantId(String participantId) {
        this.participantId = participantId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getParticipantName() {
        return participantName;
    }

    public void setParticipantName(String participantName) {
        this.participantName = participantName;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public String getParticipantType() {
        return participantType;
    }

    public void setParticipantType(String participantType) {
        this.participantType = participantType;
    }

    public String getServerData() {
        return serverData;
    }

    public void setServerData(String serverData) {
        this.serverData = serverData;
    }

    public Date getJoinDateTime() {
        return joinDateTime;
    }

    public void setJoinDateTime(Date joinDateTime) {
        this.joinDateTime = joinDateTime;
    }

    public Date getLeaveDateTime() {
        return leaveDateTime;
    }

    public void setLeaveDateTime(Date leaveDateTime) {
        this.leaveDateTime = leaveDateTime;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public String getLeaveReason() {
        return leaveReason;
    }

    public void setLeaveReason(String leaveReason) {
        this.leaveReason = leaveReason;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getParticipantKey() {
        return participantKey;
    }

    public void setParticipantKey(String participantKey) {
        this.participantKey = participantKey;
    }

    public String getParticipantRole() {
        return participantRole;
    }

    public void setParticipantRole(String participantRole) {
        this.participantRole = participantRole;
    }

    public RubruSession getRubruSession() {
        return rubruSession;
    }

    public void setRubruSession(RubruSession rubruSession) {
        this.rubruSession = rubruSession;
    }

    @Override
    public Long fetchPrimaryKey() {
        return getId();
    }
}
