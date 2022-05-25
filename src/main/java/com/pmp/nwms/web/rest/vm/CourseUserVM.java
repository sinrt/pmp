package com.pmp.nwms.web.rest.vm;

import com.pmp.nwms.config.Constants;
import com.pmp.nwms.logging.IgnoreLoggingValue;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class CourseUserVM {
    private Long id;
    @NotNull
    @Size(min = 1, max = 50)
    private String login;
    @NotNull
    @Size(max = 50)
    private String firstName;
    @NotNull
    @Size(max = 50)
    private String lastName;

    @Size(min = Constants.PASSWORD_MIN_LENGTH, max = Constants.PASSWORD_MAX_LENGTH)
    @IgnoreLoggingValue
    private String password;

    @NotNull
    private Long courseId;

    @Valid
    @NotNull
    private List<CourseUserClassroomVM> classrooms;

    @Size(max = 255)
    private String returnUrl;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public List<CourseUserClassroomVM> getClassrooms() {
        return classrooms;
    }

    public void setClassrooms(List<CourseUserClassroomVM> classrooms) {
        this.classrooms = classrooms;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }
}
