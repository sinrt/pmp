package com.pmp.nwms.web.rest.vm;

import com.pmp.nwms.config.Constants;
import com.pmp.nwms.domain.Classroom;
import com.pmp.nwms.domain.Course;
import com.pmp.nwms.domain.User;
import com.pmp.nwms.logging.IgnoreLoggingValue;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class ClassroomStudentFileVM {
    private User user;
    private Course course;
    private Classroom classroom;
    @NotNull
    @Pattern(regexp = Constants.LOGIN_REGEX)
    @Size(min = 3, max = 50)
    private String login;
    @Pattern(regexp = Constants.PARTICIPANT_NAME_REGEX)
    @Size(min = 3, max = 50)
    private String firstName;
    @Pattern(regexp = Constants.PARTICIPANT_NAME_REGEX)
    @Size(min = 3, max = 50)
    private String lastName;
    @NotNull
    @Size(min = Constants.PASSWORD_MIN_LENGTH, max = Constants.PASSWORD_MAX_LENGTH)
    @IgnoreLoggingValue
    private String password;
    private String authorityName;
    private boolean needsLogin = false;
    private Integer maxUseCount;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Classroom getClassroom() {
        return classroom;
    }

    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
}
