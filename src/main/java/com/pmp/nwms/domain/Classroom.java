package com.pmp.nwms.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pmp.nwms.config.Constants;
import com.pmp.nwms.domain.enums.ClassroomRecordingMode;
import com.pmp.nwms.util.date.DateUtil;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A Classroom.
 */
@Entity
@Table(name = "classroom")
public class Classroom extends AbstractAuditingEntity<Long> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @NotNull
    @Column(name = "session_uuid_name", length = 200, nullable = false, unique = true)
    private String sessionUuidName;

    @Column(name = "generate_random_name", length = 200, nullable = false)
    private Boolean generateRandomName;

    @Column(name = "guest_session", nullable = true)
    private boolean guestSession;

    @Column(name = "guest_session_pass", nullable = true)
    private boolean guestSessionReqPass;


    @Size(min = 1, max = 50)
    @Column(name = "guest_password", length = 50)
    private String guestPassword;

    @NotNull
    @Column(name = "start_time", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_TIME_PATTERN, timezone = Constants.SYSTEM_TIME_ZONE)
    private Date startDateTime;

    @NotNull
    @Column(name = "finish_time", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_TIME_PATTERN, timezone = Constants.SYSTEM_TIME_ZONE)
    private Date finishDateTime;

    @NotNull
    @Column(name = "connection_type", nullable = false)
    private String connectionType;

    @NotNull
    @Column(name = "resolution", nullable = true)
    private String resolution;

    @NotNull
    @Column(name = "framerate", nullable = true)
    private Integer frameRate;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @NotNull
    @JsonIgnoreProperties("")
    private User master;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("classrooms")
    private User creator;

    @Column(name = "jhi_lock")
    private Boolean lock;


    @Column(name = "is_guest_with_subscriber_role")
    private Boolean isGuestWithSubscriberRole;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("classrooms")
    private User lastModifier;

    @JsonIgnore
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.classroom")
    private Set<ClassroomStudent> classroomStudents = new HashSet<>();

    @Column(name = "use_enter_token")
    private Boolean useEnterToken = false;

    @Column(name = "hide_global_chat")
    private Boolean hideGlobalChat = false;

    @Column(name = "hide_private_chat")
    private Boolean hidePrivateChat = false;

    @Column(name = "hide_participants_list")
    private Boolean hideParticipantsList = false;

    @Column(name = "disable_file_transfer")
    private Boolean disableFileTransfer = false;

    @Column(name = "hide_sound_sensitive")
    private Boolean hideSoundSensitive = false;

    @Column(name = "hide_publish_permit")
    private Boolean hidePublishPermit = false;

    @Column(name = "enable_subscriber_direct_enter")
    private Boolean enableSubscriberDirectEnter = false;

    @Column(name = "publisher_must_enter_first")
    private Boolean publisherMustEnterFirst = false;

    @Column(name = "max_user_count")
    private Integer maxUserCount;

    @Column(name = "hide_screen")
    private Boolean hideScreen = false;

    @Column(name = "hide_whiteboard")
    private Boolean hideWhiteboard = false;

    @Column(name = "hide_slide")
    private Boolean hideSlide = false;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "recording_mode")
    private ClassroomRecordingMode recordingMode;

    @Column(name = "moderator_auto_login", nullable = true)
    private Boolean moderatorAutoLogin;

    @Column(name = "secret_key", length = 200)
    private String secretKey;

    @Column(name = "session_active", nullable = false)
    private boolean sessionActive;

    @Column(name = "signal_session_end", nullable = false)
    private boolean signalSessionEnd;


    @Column(name = "return_url", length = 255, nullable = true)
    private String returnUrl;

    @Column(name = "outer_manage", nullable = false)
    private boolean outerManage = false;

    @Column(name = "non_manager_enter_sound_off", nullable = false)
    private boolean nonManagerEnterSoundOff = false;

    @Column(name = "non_manager_enter_video_off", nullable = false)
    private boolean nonManagerEnterVideoOff = false;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Classroom name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(Date startDateTime) {
        this.startDateTime = startDateTime;
    }

    public Date getFinishDateTime() {
        return finishDateTime;
    }

    public void setFinishDateTime(Date finishDateTime) {
        this.finishDateTime = finishDateTime;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public boolean isGuestSession() {
        return guestSession;
    }

    public void setGuestSession(boolean guestSession) {
        this.guestSession = guestSession;
    }

    public Boolean getLock() {
        if (lock == null) {
            lock = false;
        }
        return lock;
    }

    public void setLock(Boolean lock) {
        this.lock = lock;
    }

    public String getGuestPassword() {
        return guestPassword;
    }

    public void setGuestPassword(String guestPassword) {
        this.guestPassword = guestPassword;
    }

    public boolean isGuestSessionReqPass() {
        return guestSessionReqPass;
    }

    public Boolean getIsGuestWithSubscriberRole() {
        if (isGuestWithSubscriberRole == null) {
            isGuestWithSubscriberRole = false;
        }
        return isGuestWithSubscriberRole;
    }

    public void setIsGuestWithSubscriberRole(Boolean guestWithSubscriberRole) {
        isGuestWithSubscriberRole = guestWithSubscriberRole;
    }

    public void setGuestSessionReqPass(boolean guestSessionReqPass) {
        this.guestSessionReqPass = guestSessionReqPass;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Set<ClassroomStudent> getClassroomStudents() {
        return classroomStudents;
    }

    public void setClassroomStudents(Set<ClassroomStudent> classroomStudents) {
        this.classroomStudents = classroomStudents;
    }


    @Transient
    public Instant getStartTime() {
        if (startDateTime != null) {
            return startDateTime.toInstant();
        }
        return null;
    }

    @Transient
    public Classroom startTime(Instant startTime) {
        if (startTime != null) {
            this.startDateTime = DateUtil.convertInstantToDate(startTime);
        }
        return this;
    }

    @Transient
    public void setStartTime(Instant startTime) {
        this.startDateTime = DateUtil.convertInstantToDate(startTime);
    }

    @Transient
    public Instant getFinishTime() {
        if (finishDateTime != null) {
            return finishDateTime.toInstant();
        }
        return null;
    }

    @Transient
    public Classroom finishTime(Instant finishTime) {
        if (finishTime != null) {
            this.startDateTime = DateUtil.convertInstantToDate(finishTime);
        }
        return this;
    }

    @Transient
    public void setFinishTime(Instant finishTime) {
        this.finishDateTime = DateUtil.convertInstantToDate(finishTime);
    }

    public String getConnectionType() {
        return connectionType;
    }

    public Classroom connectionType(String connectionType) {
        this.connectionType = connectionType;
        return this;
    }

    public void setConnectionType(String connectionType) {
        this.connectionType = connectionType;
    }

    public User getMaster() {
        return master;
    }

    public Classroom master(User user) {
        this.master = user;
        return this;
    }

    public Classroom course(Course course) {
        this.course = course;
        return this;
    }

    public void setMaster(User user) {
        this.master = user;
    }


    public User getCreator() {
        return creator;
    }

    public Classroom creator(User user) {
        this.creator = user;
        return this;
    }

    public void setCreator(User user) {
        this.creator = user;
    }

    public User getLastModifier() {
        return lastModifier;
    }

    public Classroom lastModifier(User lastModifier) {
        this.lastModifier = lastModifier;
        return this;
    }

    public String getSessionUuidName() {
        return sessionUuidName;
    }

    public void setSessionUuidName(String sessionUuidName) {
        this.sessionUuidName = sessionUuidName;
    }

    public Boolean getGenerateRandomName() {
        return generateRandomName;
    }

    public void setGenerateRandomName(Boolean generateRandomName) {
        this.generateRandomName = generateRandomName;
    }

    public Integer getFrameRate() {
        return frameRate;
    }

    public void setFrameRate(Integer frameRate) {
        this.frameRate = frameRate;
    }

    public void setLastModifier(User lastModifier) {
        this.lastModifier = lastModifier;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove


    public Boolean getUseEnterToken() {
        if (useEnterToken == null) {
            useEnterToken = false;
        }
        return useEnterToken;
    }

    public void setUseEnterToken(Boolean useEnterToken) {
        this.useEnterToken = useEnterToken;
    }


    public Boolean getHideGlobalChat() {
        if (hideGlobalChat == null) {
            hideGlobalChat = false;
        }
        return hideGlobalChat;
    }

    public void setHideGlobalChat(Boolean hideGlobalChat) {
        this.hideGlobalChat = hideGlobalChat;
    }

    public Boolean getHidePrivateChat() {
        if (hidePrivateChat == null) {
            hidePrivateChat = false;
        }
        return hidePrivateChat;
    }

    public void setHidePrivateChat(Boolean hidePrivateChat) {
        this.hidePrivateChat = hidePrivateChat;
    }

    public Boolean getHideParticipantsList() {
        if (hideParticipantsList == null) {
            hideParticipantsList = false;
        }
        return hideParticipantsList;
    }

    public void setHideParticipantsList(Boolean hideParticipantsList) {
        this.hideParticipantsList = hideParticipantsList;
    }

    public Boolean getDisableFileTransfer() {
        if (disableFileTransfer == null) {
            disableFileTransfer = false;
        }
        return disableFileTransfer;
    }

    public void setDisableFileTransfer(Boolean disableFileTransfer) {
        this.disableFileTransfer = disableFileTransfer;
    }

    public Boolean getHideSoundSensitive() {
        if (hideSoundSensitive == null) {
            hideSoundSensitive = false;
        }
        return hideSoundSensitive;
    }

    public void setHideSoundSensitive(Boolean hideSoundSensitive) {
        this.hideSoundSensitive = hideSoundSensitive;
    }

    public Boolean getHidePublishPermit() {
        if (hidePublishPermit == null) {
            hidePublishPermit = false;
        }
        return hidePublishPermit;
    }

    public void setHidePublishPermit(Boolean hidePublishPermit) {
        this.hidePublishPermit = hidePublishPermit;
    }

    public Boolean getEnableSubscriberDirectEnter() {
        if (enableSubscriberDirectEnter == null) {
            enableSubscriberDirectEnter = false;
        }
        return enableSubscriberDirectEnter;
    }

    public void setEnableSubscriberDirectEnter(Boolean enableSubscriberDirectEnter) {
        this.enableSubscriberDirectEnter = enableSubscriberDirectEnter;
    }

    public Boolean getPublisherMustEnterFirst() {
        if (publisherMustEnterFirst == null) {
            publisherMustEnterFirst = false;
        }
        return publisherMustEnterFirst;
    }

    public void setPublisherMustEnterFirst(Boolean publisherMustEnterFirst) {
        this.publisherMustEnterFirst = publisherMustEnterFirst;
    }

    public Integer getMaxUserCount() {
        return maxUserCount;
    }

    public void setMaxUserCount(Integer maxUserCount) {
        this.maxUserCount = maxUserCount;
    }

    public Boolean getHideScreen() {
        if (hideScreen == null) {
            hideScreen = false;
        }
        return hideScreen;
    }

    public void setHideScreen(Boolean hideScreen) {
        this.hideScreen = hideScreen;
    }

    public Boolean getHideWhiteboard() {
        if (hideWhiteboard == null) {
            hideWhiteboard = false;
        }
        return hideWhiteboard;
    }

    public void setHideWhiteboard(Boolean hideWhiteboard) {
        this.hideWhiteboard = hideWhiteboard;
    }

    public Boolean getHideSlide() {
        if (hideSlide == null) {
            hideSlide = false;
        }
        return hideSlide;
    }

    public void setHideSlide(Boolean hideSlide) {
        this.hideSlide = hideSlide;
    }

    public ClassroomRecordingMode getRecordingMode() {
        return recordingMode;
    }

    public void setRecordingMode(ClassroomRecordingMode recordingMode) {
        this.recordingMode = recordingMode;
    }

    public Boolean getModeratorAutoLogin() {
        return moderatorAutoLogin;
    }

    public void setModeratorAutoLogin(Boolean moderatorAutoLogin) {
        this.moderatorAutoLogin = moderatorAutoLogin;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public boolean isSessionActive() {
        return sessionActive;
    }

    public void setSessionActive(boolean sessionActive) {
        this.sessionActive = sessionActive;
    }

    public boolean isSignalSessionEnd() {
        return signalSessionEnd;
    }

    public void setSignalSessionEnd(boolean signalSessionEnd) {
        this.signalSessionEnd = signalSessionEnd;
    }


    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public boolean isOuterManage() {
        return outerManage;
    }

    public void setOuterManage(boolean outerManage) {
        this.outerManage = outerManage;
    }

    public boolean isNonManagerEnterSoundOff() {
        return nonManagerEnterSoundOff;
    }

    public void setNonManagerEnterSoundOff(boolean nonManagerEnterSoundOff) {
        this.nonManagerEnterSoundOff = nonManagerEnterSoundOff;
    }

    public boolean isNonManagerEnterVideoOff() {
        return nonManagerEnterVideoOff;
    }

    public void setNonManagerEnterVideoOff(boolean nonManagerEnterVideoOff) {
        this.nonManagerEnterVideoOff = nonManagerEnterVideoOff;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Classroom classroom = (Classroom) o;
        if (classroom.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), classroom.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Classroom{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", startTime='" + getStartTime() + "'" +
            ", finishTime='" + getFinishTime() + "'" +
            "}";
    }

    @Override
    public Long fetchPrimaryKey() {
        return getId();
    }
}
