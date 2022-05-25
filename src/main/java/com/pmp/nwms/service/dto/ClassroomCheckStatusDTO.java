package com.pmp.nwms.service.dto;

import com.pmp.nwms.domain.enums.ClassroomRecordingMode;

import java.io.Serializable;
import java.util.Date;

public interface ClassroomCheckStatusDTO extends Serializable {
    Long getId();

    String getName();

    boolean isGuestSession();

    boolean isTeacherPan();

    Boolean getUseEnterToken();

    Long getCreatorId();

    Date getStartDateTime();

    Date getFinishDateTime();

    Integer getMaxUserCount();

    Boolean getHideGlobalChat();

    Boolean getHidePrivateChat();

    Boolean getHideParticipantsList();

    Boolean getDisableFileTransfer();

    Boolean getHideSoundSensitive();

    Boolean getHidePublishPermit();

    Boolean getEnableSubscriberDirectEnter();

    Boolean getPublisherMustEnterFirst();

    Boolean getLocked();

    Boolean getIsGuestWithSubscriberRole();

    Boolean getHideScreen();

    Boolean getHideWhiteboard();

    Boolean getHideSlide();

    ClassroomRecordingMode getRecordingMode();

    Boolean getModeratorAutoLogin();

    String getSecretKey();

    boolean isSessionActive();

    boolean isSignalSessionEnd();

    String getReturnUrl();

    boolean isOuterManage();

    boolean isNonManagerEnterSoundOff();

    boolean isNonManagerEnterVideoOff();

}
