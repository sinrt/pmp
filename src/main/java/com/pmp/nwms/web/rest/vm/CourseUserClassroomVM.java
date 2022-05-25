package com.pmp.nwms.web.rest.vm;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CourseUserClassroomVM {
    @NotNull
    private Long classroomId;
    @NotNull
    @Size(max = 50)
    private String authorityName;
    @NotNull
    private Boolean needsLogin;
    private Integer maxUseCount;

    public Long getClassroomId() {
        return classroomId;
    }

    public void setClassroomId(Long classroomId) {
        this.classroomId = classroomId;
    }

    public String getAuthorityName() {
        return authorityName;
    }

    public void setAuthorityName(String authorityName) {
        this.authorityName = authorityName;
    }

    public Boolean getNeedsLogin() {
        return needsLogin;
    }

    public void setNeedsLogin(Boolean needsLogin) {
        this.needsLogin = needsLogin;
    }

    public Integer getMaxUseCount() {
        return maxUseCount;
    }

    public void setMaxUseCount(Integer maxUseCount) {
        this.maxUseCount = maxUseCount;
    }
}
