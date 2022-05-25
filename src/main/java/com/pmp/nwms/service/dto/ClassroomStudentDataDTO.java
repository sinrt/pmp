package com.pmp.nwms.service.dto;

import com.pmp.nwms.model.SpecialLinkHolderModel;

public class ClassroomStudentDataDTO implements ClassroomStudentDTO, SpecialLinkHolderModel {
    private Long classroomId;
    private String classroomName;
    private String classroomSessionUuidName;
    private String authorityName;
    private boolean needsLogin;
    private Integer maxUseCount;
    private String token;
    private Long creatorId;
    private String specialLink;

    public ClassroomStudentDataDTO() {
    }

    public ClassroomStudentDataDTO(ClassroomStudentDTO dto) {
        this.classroomId = dto.getClassroomId();
        this.classroomName = dto.getClassroomName();
        this.classroomSessionUuidName = dto.getClassroomSessionUuidName();
        this.authorityName = dto.getAuthorityName();
        this.needsLogin = dto.isNeedsLogin();
        this.maxUseCount = dto.getMaxUseCount();
        this.token = dto.getToken();
        this.creatorId = dto.getCreatorId();
    }

    @Override
    public Long getClassroomId() {
        return classroomId;
    }

    @Override
    public String getClassroomName() {
        return classroomName;
    }

    @Override
    public String getClassroomSessionUuidName() {
        return classroomSessionUuidName;
    }

    @Override
    public String getAuthorityName() {
        return authorityName;
    }

    @Override
    public boolean isNeedsLogin() {
        return needsLogin;
    }

    @Override
    public Integer getMaxUseCount() {
        return maxUseCount;
    }

    @Override
    public String getToken() {
        return token;
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
}
