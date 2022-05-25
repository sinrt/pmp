package com.pmp.nwms.web.rest.vm;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public class CourseBatchUserVM {
    @NotNull
    @NotEmpty
    private List<Long> userIds;
    @NotNull
    private Long courseId;

    private Long classroomId;

    private String authorityName;

    private Boolean needsLogin;

    private Integer maxUseCount;


    public List<Long> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<Long> userIds) {
        this.userIds = userIds;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

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

    public boolean hasClassroomDataComplete() {
        return classroomId != null
            && authorityName != null && !authorityName.isEmpty()
            && needsLogin != null
            && maxUseCount != null;
    }

    public boolean hasClassroomDataPartial() {
        return !hasClassroomDataComplete()
            && (classroomId != null
            || (authorityName != null && !authorityName.isEmpty())
            || needsLogin != null
            || maxUseCount != null);
    }

}
