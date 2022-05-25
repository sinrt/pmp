package com.pmp.nwms.domain;


import com.pmp.nwms.domain.enums.ClassroomRecordingMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * A Classroom.
 */
@Entity
@Table(name = "del_classroom")
public class DeletedClassroom extends AbstractDeletedEntity<Long> {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Column(name = "sessionUuidName", length = 200, nullable = false)
    private String sessionUuidName;

    @Column(name = "guest_session", nullable = true)
    private boolean guestSession;

    @Column(name = "guest_session_pass", nullable = true)
    private boolean guestSessionReqPass;

    @Column(name = "guest_password", length = 50)
    private String guestPassword;

    @Column(name = "start_time", nullable = false)
    private Date startDateTime;

    @Column(name = "finish_time", nullable = false)
    private Date finishDateTime;

    @Column(name = "connection_type", nullable = false)
    private String connectionType;

    @Column(name = "resolution", nullable = true)
    private String resolution;

    @Column(name = "framerate", nullable = true)
    private Integer frameRate;

    @Column(name = "master_id")
    private Long masterId;

    @Column(name = "creator_id")
    private Long creatorId;

    @Column(name = "jhi_lock")
    private Boolean lock;


    @Column(name = "is_guest_with_subscriber_role")
    private Boolean isGuestWithSubscriberRole;

    @Column(name = "last_modifier_id")
    private Long lastModifierId;

    @Column(name = "course_id")
    private Long courseId;

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

    public void setName(String name) {
        this.name = name;
    }

    public String getSessionUuidName() {
        return sessionUuidName;
    }

    public void setSessionUuidName(String sessionUuidName) {
        this.sessionUuidName = sessionUuidName;
    }

    public boolean isGuestSession() {
        return guestSession;
    }

    public void setGuestSession(boolean guestSession) {
        this.guestSession = guestSession;
    }

    public boolean isGuestSessionReqPass() {
        return guestSessionReqPass;
    }

    public void setGuestSessionReqPass(boolean guestSessionReqPass) {
        this.guestSessionReqPass = guestSessionReqPass;
    }

    public String getGuestPassword() {
        return guestPassword;
    }

    public void setGuestPassword(String guestPassword) {
        this.guestPassword = guestPassword;
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

    public String getConnectionType() {
        return connectionType;
    }

    public void setConnectionType(String connectionType) {
        this.connectionType = connectionType;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public Integer getFrameRate() {
        return frameRate;
    }

    public void setFrameRate(Integer frameRate) {
        this.frameRate = frameRate;
    }

    public Long getMasterId() {
        return masterId;
    }

    public void setMasterId(Long masterId) {
        this.masterId = masterId;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public Boolean getLock() {
        return lock;
    }

    public void setLock(Boolean lock) {
        this.lock = lock;
    }

    public Boolean getGuestWithSubscriberRole() {
        return isGuestWithSubscriberRole;
    }

    public void setGuestWithSubscriberRole(Boolean guestWithSubscriberRole) {
        isGuestWithSubscriberRole = guestWithSubscriberRole;
    }

    public Long getLastModifierId() {
        return lastModifierId;
    }

    public void setLastModifierId(Long lastModifierId) {
        this.lastModifierId = lastModifierId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Boolean getUseEnterToken() {
        return useEnterToken;
    }

    public void setUseEnterToken(Boolean useEnterToken) {
        this.useEnterToken = useEnterToken;
    }

    public Boolean getHideGlobalChat() {
        return hideGlobalChat;
    }

    public void setHideGlobalChat(Boolean hideGlobalChat) {
        this.hideGlobalChat = hideGlobalChat;
    }

    public Boolean getHidePrivateChat() {
        return hidePrivateChat;
    }

    public void setHidePrivateChat(Boolean hidePrivateChat) {
        this.hidePrivateChat = hidePrivateChat;
    }

    public Boolean getHideParticipantsList() {
        return hideParticipantsList;
    }

    public void setHideParticipantsList(Boolean hideParticipantsList) {
        this.hideParticipantsList = hideParticipantsList;
    }

    public Boolean getDisableFileTransfer() {
        return disableFileTransfer;
    }

    public void setDisableFileTransfer(Boolean disableFileTransfer) {
        this.disableFileTransfer = disableFileTransfer;
    }

    public Boolean getHideSoundSensitive() {
        return hideSoundSensitive;
    }

    public void setHideSoundSensitive(Boolean hideSoundSensitive) {
        this.hideSoundSensitive = hideSoundSensitive;
    }

    public Boolean getHidePublishPermit() {
        return hidePublishPermit;
    }

    public void setHidePublishPermit(Boolean hidePublishPermit) {
        this.hidePublishPermit = hidePublishPermit;
    }

    public Boolean getEnableSubscriberDirectEnter() {
        return enableSubscriberDirectEnter;
    }

    public void setEnableSubscriberDirectEnter(Boolean enableSubscriberDirectEnter) {
        this.enableSubscriberDirectEnter = enableSubscriberDirectEnter;
    }

    public Boolean getPublisherMustEnterFirst() {
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
        return hideScreen;
    }

    public void setHideScreen(Boolean hideScreen) {
        this.hideScreen = hideScreen;
    }

    public Boolean getHideWhiteboard() {
        return hideWhiteboard;
    }

    public void setHideWhiteboard(Boolean hideWhiteboard) {
        this.hideWhiteboard = hideWhiteboard;
    }

    public Boolean getHideSlide() {
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
    public Long fetchPrimaryKey() {
        return getId();
    }
}
