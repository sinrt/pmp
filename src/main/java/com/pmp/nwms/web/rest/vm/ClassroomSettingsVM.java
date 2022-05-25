package com.pmp.nwms.web.rest.vm;

import javax.validation.constraints.NotNull;

public class ClassroomSettingsVM {
    @NotNull
    private Long classroomId;

    private Boolean hideGlobalChat;
    private Boolean hidePrivateChat;
    private Boolean hideParticipantsList;
    private Boolean disableFileTransfer;
    private Boolean hideSoundSensitive;
    private Boolean hidePublishPermit;
    private Boolean lock;
    private Boolean hideScreen;
    private Boolean hideWhiteboard;
    private Boolean hideSlide;

    public Long getClassroomId() {
        return classroomId;
    }

    public void setClassroomId(Long classroomId) {
        this.classroomId = classroomId;
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

    public boolean hasData() {
        return hideGlobalChat != null
            || hidePrivateChat != null
            || hideParticipantsList != null
            || disableFileTransfer != null
            || hideSoundSensitive != null
            || hidePublishPermit != null
            || lock != null
            || hideScreen != null
            || hideWhiteboard != null
            || hideSlide != null;
    }
}
