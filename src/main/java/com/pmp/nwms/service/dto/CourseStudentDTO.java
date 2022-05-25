package com.pmp.nwms.service.dto;

import java.util.List;

public class CourseStudentDTO {
    private Long userId;
    private String login;
    private String firstName;
    private String lastName;
    private List<ClassroomStudentDataDTO> classroomStudents;

    public CourseStudentDTO() {
    }

    public CourseStudentDTO(CourseStudentDataDTO dto) {
        this.userId = dto.getUserId();
        this.login = dto.getLogin();
        this.firstName = dto.getFirstName();
        this.lastName = dto.getLastName();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public List<ClassroomStudentDataDTO> getClassroomStudents() {
        return classroomStudents;
    }

    public void setClassroomStudents(List<ClassroomStudentDataDTO> classroomStudents) {
        this.classroomStudents = classroomStudents;
    }
}
