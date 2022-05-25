package com.pmp.nwms.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "classroom_blocked_client",
    indexes = {
    @Index(name = "idx_classroom_client", columnList = "classroom_id DESC, client_id"),
    @Index(name = "idx_classroom_user", columnList = "classroom_id DESC, blocker_user_id")
})
public class ClassroomBlockedClient implements Serializable, BaseEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "client_id", length = 50, nullable = false)
    private String clientId;

    @Basic
    @Column(name = "blocker_user_id", nullable = false)
    private Long blockerUserId;

    @Basic
    @Column(name = "blocked_user_id", nullable = true)
    private Long blockedUserId;

    @Basic
    @Column(name = "block_time", nullable = false)
    private Date blockTime;

    @Basic
    @Column(name = "participant_name", length = 255, nullable = true)
    private String participantName;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "classroom_id")
    private Classroom classroom;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public Long getBlockerUserId() {
        return blockerUserId;
    }

    public void setBlockerUserId(Long blockerUserId) {
        this.blockerUserId = blockerUserId;
    }

    public Long getBlockedUserId() {
        return blockedUserId;
    }

    public void setBlockedUserId(Long blockedUserId) {
        this.blockedUserId = blockedUserId;
    }

    public Date getBlockTime() {
        return blockTime;
    }

    public void setBlockTime(Date blockTime) {
        this.blockTime = blockTime;
    }

    public String getParticipantName() {
        return participantName;
    }

    public void setParticipantName(String participantName) {
        this.participantName = participantName;
    }

    public Classroom getClassroom() {
        return classroom;
    }

    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }

    @Override
    public Long fetchPrimaryKey() {
        return getId();
    }
}
