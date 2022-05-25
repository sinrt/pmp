package com.pmp.nwms.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pmp.nwms.config.Constants;
import com.pmp.nwms.domain.enums.ClassroomRecordingMode;
import com.pmp.nwms.model.SpecialLinkHolderModel;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.TimeZone;

public class WebinarClassroomDTO implements SpecialLinkHolderModel {
    private Long id;
    @NotNull
    @Size(min = 1, max = 100)
    private String name;

    @Size(min = 0, max = 200)
    private String sessionUuidName;

    private Boolean generateRandomName;

    @NotNull
    private boolean guestSession;
    @NotNull
    private boolean guestWithSubscriberRole;
    @NotNull
    private Boolean useEnterToken;
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_TIME_PATTERN, timezone = Constants.SYSTEM_TIME_ZONE)
    private Date startDateTime;
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_TIME_PATTERN, timezone = Constants.SYSTEM_TIME_ZONE)
    private Date finishDateTime;
    @NotNull
    private Long courseId;
    @NotNull
    private Boolean hideGlobalChat;
    @NotNull
    private Boolean hidePrivateChat;
    @NotNull
    private Boolean hideParticipantsList;
    @NotNull
    private Boolean disableFileTransfer;
    @NotNull
    private Boolean hideSoundSensitive;
    @NotNull
    private Boolean hidePublishPermit;
    @NotNull
    private Boolean enableSubscriberDirectEnter;
    @NotNull
    private Boolean publisherMustEnterFirst;

    private Boolean lock;

    private Integer maxUserCount;

    private Boolean hideScreen;

    private Boolean hideWhiteboard;

    private Boolean hideSlide;

    private ClassroomRecordingMode recordingMode;

    private Boolean moderatorAutoLogin;

    @Size(min = 0, max = 200)
    private String secretKey;

    @Size(min = 0, max = 255)
    private String returnUrl;

    private Boolean signalSessionEnd;

    private Long creatorId;

    private String specialLink;

    private Boolean outerManage;

    private Boolean nonManagerEnterSoundOff;

    private Boolean nonManagerEnterVideoOff;


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

    public Boolean getGenerateRandomName() {
        if(generateRandomName == null){
            generateRandomName = true;
        }
        return generateRandomName;
    }

    public void setGenerateRandomName(Boolean generateRandomName) {
        this.generateRandomName = generateRandomName;
    }

    public boolean isGuestSession() {
        return guestSession;
    }

    public void setGuestSession(boolean guestSession) {
        this.guestSession = guestSession;
    }

    public boolean isGuestWithSubscriberRole() {
        return guestWithSubscriberRole;
    }

    public void setGuestWithSubscriberRole(boolean guestWithSubscriberRole) {
        this.guestWithSubscriberRole = guestWithSubscriberRole;
    }

    public Boolean getUseEnterToken() {
        return useEnterToken;
    }

    public void setUseEnterToken(Boolean useEnterToken) {
        this.useEnterToken = useEnterToken;
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

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
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

    public Boolean getLock() {
        return lock;
    }

    public void setLock(Boolean lock) {
        this.lock = lock;
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

    public Boolean getSignalSessionEnd() {
        return signalSessionEnd;
    }

    public void setSignalSessionEnd(Boolean signalSessionEnd) {
        this.signalSessionEnd = signalSessionEnd;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    @Override
    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    @Override
    public String getSpecialLink() {
        return specialLink;
    }

    @Override
    public void setSpecialLink(String specialLink) {
        this.specialLink = specialLink;
    }

    public Boolean getOuterManage() {
        return outerManage;
    }

    public void setOuterManage(Boolean outerManage) {
        this.outerManage = outerManage;
    }

    public Boolean getNonManagerEnterSoundOff() {
        return nonManagerEnterSoundOff;
    }

    public void setNonManagerEnterSoundOff(Boolean nonManagerEnterSoundOff) {
        this.nonManagerEnterSoundOff = nonManagerEnterSoundOff;
    }

    public Boolean getNonManagerEnterVideoOff() {
        return nonManagerEnterVideoOff;
    }

    public void setNonManagerEnterVideoOff(Boolean nonManagerEnterVideoOff) {
        this.nonManagerEnterVideoOff = nonManagerEnterVideoOff;
    }
}
