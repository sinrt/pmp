package com.pmp.nwms.service.dto;

import com.pmp.nwms.config.Constants;
import com.pmp.nwms.logging.IgnoreLoggingValue;
import com.pmp.nwms.web.rest.vm.ManagedUserVM;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class WebinarClassroomStudentDTO {
    private Long userId;

    @NotNull
    private Long classroomId;

    @NotNull
    @Pattern(regexp = Constants.LOGIN_REGEX)
    @Size(min = 1, max = 50)
    private String login;

    @NotNull
    @Size(min = Constants.PASSWORD_MIN_LENGTH, max = Constants.PASSWORD_MAX_LENGTH)
    @IgnoreLoggingValue
    private String password;

    @NotNull
    @Size(max = 50)
    private String firstName;

    @NotNull
    @Size(max = 50)
    private String lastName;

    @NotNull
    private String authorityName;
    @NotNull
    private boolean needsLogin = false;

    private Integer maxUseCount;

    private Boolean dynamicStudent = false;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getClassroomId() {
        return classroomId;
    }

    public void setClassroomId(Long classroomId) {
        this.classroomId = classroomId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAuthorityName() {
        return authorityName;
    }

    public void setAuthorityName(String authorityName) {
        this.authorityName = authorityName;
    }

    public boolean isNeedsLogin() {
        return needsLogin;
    }

    public void setNeedsLogin(boolean needsLogin) {
        this.needsLogin = needsLogin;
    }

    public Integer getMaxUseCount() {
        return maxUseCount;
    }

    public void setMaxUseCount(Integer maxUseCount) {
        this.maxUseCount = maxUseCount;
    }

    public Boolean getDynamicStudent() {
        if(dynamicStudent == null){
            dynamicStudent = false;
        }
        return dynamicStudent;
    }

    public void setDynamicStudent(Boolean dynamicStudent) {
        this.dynamicStudent = dynamicStudent;
    }
}
