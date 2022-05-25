package com.pmp.nwms.service.listmodel;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pmp.nwms.config.Constants;
import com.pmp.nwms.model.SpecialLinkHolderModel;

import java.util.Date;

public class UserClassroomDataListModel implements UserClassroomListModel, SpecialLinkHolderModel {
    private Long id;
    private String name;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_TIME_PATTERN, timezone = Constants.SYSTEM_TIME_ZONE)
    private Date startDateTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_TIME_PATTERN, timezone = Constants.SYSTEM_TIME_ZONE)
    private Date finishDateTime;
    private String sessionUuidName;
    private boolean teacherPan;
    private Boolean useEnterToken;
    private String authorityName;
    private String token;
    private Long creatorId;
    private String specialLink;

    public UserClassroomDataListModel() {
    }

    public UserClassroomDataListModel(UserClassroomListModel model) {
        this.id = model.getId();
        this.name = model.getName();
        this.startDateTime = model.getStartDateTime();
        this.finishDateTime = model.getFinishDateTime();
        this.sessionUuidName = model.getSessionUuidName();
        this.teacherPan = model.isTeacherPan();
        this.useEnterToken = model.getUseEnterToken();
        this.authorityName = model.getAuthorityName();
        this.token = model.getToken();
        this.creatorId = model.getCreatorId();
    }

    @Override
    public Long getCreatorId() {
        return creatorId;
    }

    @Override
    public String getSpecialLink() {
        return specialLink;
    }

    @Override
    public void setSpecialLink(String specialLink) {
        this.specialLink = specialLink;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Date getStartDateTime() {
        return startDateTime;
    }

    @Override
    public Date getFinishDateTime() {
        return finishDateTime;
    }

    @Override
    public String getSessionUuidName() {
        return sessionUuidName;
    }

    @Override
    public boolean isTeacherPan() {
        return teacherPan;
    }

    @Override
    public Boolean getUseEnterToken() {
        return useEnterToken;
    }

    @Override
    public String getAuthorityName() {
        return authorityName;
    }

    @Override
    public String getToken() {
        return token;
    }
}
