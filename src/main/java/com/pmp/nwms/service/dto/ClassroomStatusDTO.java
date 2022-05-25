package com.pmp.nwms.service.dto;

import com.pmp.nwms.domain.enums.RecordingOutputMode;
import com.pmp.nwms.service.enums.ClassroomEntranceType;
import com.pmp.nwms.service.enums.ClassroomStatusResultCode;

public class ClassroomStatusDTO {
    private ClassroomStatusResultCode resultCode;
    private Long classroomId;
    private String classroomName;
    private String displayMessage;
    private String userFullName;
    private String userAuthorityName;

    private Integer frameRate;
    private Integer concurVideos;
    private Integer resolutionHeight;
    private Integer resolutionWidth;
    private Integer screenShareHeight;
    private Integer screenShareWidth;
    private Integer screenShareFrameRate;
    private Long userId;
    private String returnUrl;
    private String wsUrl;
    private String specialLink;
    private Boolean fileTransfer;
    private Integer qualityVeryLow;
    private Integer qualityLow;
    private Integer qualityMedium;
    private Integer qualityHigh;
    private Integer qualityVeryHigh;
    private String recordingMode;
    private RecordingOutputMode recordingDefaultOutputMode;


    private Boolean hideGlobalChat;
    private Boolean hidePrivateChat;
    private Boolean hideParticipantsList;
    private Boolean disableFileTransfer;
    private Boolean hideSoundSensitive;
    private Boolean hidePublishPermit;
    private Boolean enableSubscriberDirectEnter;
    private Boolean publisherMustEnterFirst;
    private Boolean lock;
    private Boolean hideScreen;
    private Boolean hideWhiteboard;
    private Boolean hideSlide;

    private String authToken;

    private Boolean dynamicStudent;

    private String participantKey;

    private boolean outerManage;
    private boolean nonManagerEnterSoundOff;
    private boolean nonManagerEnterVideoOff;

    public String getResultCode() {
        return resultCode.getCode();
    }

    public void setResultCode(ClassroomStatusResultCode resultCode) {
        this.resultCode = resultCode;
    }

    public ClassroomEntranceType getEntranceType() {
        return resultCode.getEntranceType();
    }

    public Long getClassroomId() {
        return classroomId;
    }

    public void setClassroomId(Long classroomId) {
        this.classroomId = classroomId;
    }

    public String getClassroomName() {
        return classroomName;
    }

    public void setClassroomName(String classroomName) {
        this.classroomName = classroomName;
    }

    public String getDisplayMessage() {
        return displayMessage;
    }

    public void setDisplayMessage(String displayMessage) {
        this.displayMessage = displayMessage;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    public String getUserAuthorityName() {
        return userAuthorityName;
    }

    public void setUserAuthorityName(String userAuthorityName) {
        this.userAuthorityName = userAuthorityName;
    }

    public Integer getFrameRate() {
        return frameRate;
    }

    public void setFrameRate(Integer frameRate) {
        this.frameRate = frameRate;
    }

    public Integer getConcurVideos() {
        return concurVideos;
    }

    public void setConcurVideos(Integer concurVideos) {
        this.concurVideos = concurVideos;
    }

    public Integer getResolutionHeight() {
        return resolutionHeight;
    }

    public void setResolutionHeight(Integer resolutionHeight) {
        this.resolutionHeight = resolutionHeight;
    }

    public Integer getResolutionWidth() {
        return resolutionWidth;
    }

    public void setResolutionWidth(Integer resolutionWidth) {
        this.resolutionWidth = resolutionWidth;
    }

    public Integer getScreenShareHeight() {
        return screenShareHeight;
    }

    public void setScreenShareHeight(Integer screenShareHeight) {
        this.screenShareHeight = screenShareHeight;
    }

    public Integer getScreenShareWidth() {
        return screenShareWidth;
    }

    public void setScreenShareWidth(Integer screenShareWidth) {
        this.screenShareWidth = screenShareWidth;
    }

    public Integer getScreenShareFrameRate() {
        return screenShareFrameRate;
    }

    public void setScreenShareFrameRate(Integer screenShareFrameRate) {
        this.screenShareFrameRate = screenShareFrameRate;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public String getWsUrl() {
        return wsUrl;
    }

    public void setWsUrl(String wsUrl) {
        this.wsUrl = wsUrl;
    }

    public String getSpecialLink() {
        return specialLink;
    }

    public void setSpecialLink(String specialLink) {
        this.specialLink = specialLink;
    }

    public Boolean getFileTransfer() {
        if (fileTransfer == null) {
            fileTransfer = false;
        }
        return fileTransfer;
    }

    public void setFileTransfer(Boolean fileTransfer) {
        this.fileTransfer = fileTransfer;
    }

    public Integer getQualityVeryLow() {
        return qualityVeryLow;
    }

    public void setQualityVeryLow(Integer qualityVeryLow) {
        this.qualityVeryLow = qualityVeryLow;
    }

    public Integer getQualityLow() {
        return qualityLow;
    }

    public void setQualityLow(Integer qualityLow) {
        this.qualityLow = qualityLow;
    }

    public Integer getQualityMedium() {
        return qualityMedium;
    }

    public void setQualityMedium(Integer qualityMedium) {
        this.qualityMedium = qualityMedium;
    }

    public Integer getQualityHigh() {
        return qualityHigh;
    }

    public void setQualityHigh(Integer qualityHigh) {
        this.qualityHigh = qualityHigh;
    }

    public Integer getQualityVeryHigh() {
        return qualityVeryHigh;
    }

    public void setQualityVeryHigh(Integer qualityVeryHigh) {
        this.qualityVeryHigh = qualityVeryHigh;
    }

    public String getRecordingMode() {
        return recordingMode;
    }

    public void setRecordingMode(String recordingMode) {
        this.recordingMode = recordingMode;
    }

    public RecordingOutputMode getRecordingDefaultOutputMode() {
        return recordingDefaultOutputMode;
    }

    public void setRecordingDefaultOutputMode(RecordingOutputMode recordingDefaultOutputMode) {
        this.recordingDefaultOutputMode = recordingDefaultOutputMode;
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

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public Boolean getDynamicStudent() {
        if (dynamicStudent == null) {
            dynamicStudent = false;
        }
        return dynamicStudent;
    }

    public void setDynamicStudent(Boolean dynamicStudent) {
        this.dynamicStudent = dynamicStudent;
    }

    public String getParticipantKey() {
        return participantKey;
    }

    public void setParticipantKey(String participantKey) {
        this.participantKey = participantKey;
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
}
