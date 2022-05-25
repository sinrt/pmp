package com.pmp.nwms.web.rest.vm;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CourseSimpleVM {
    private Long id;
    @NotNull
    @Size(min = 1, max = 50)
    private String title;
    @Size(min = 0, max = 255)
    private String description;
    @NotNull
    private boolean teacherPan;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isTeacherPan() {
        return teacherPan;
    }

    public void setTeacherPan(boolean teacherPan) {
        this.teacherPan = teacherPan;
    }

    @Override
    public String toString() {
        return "CourseSimpleVM{" +
            "id=" + id +
            ", title='" + title + '\'' +
            ", description='" + description + '\'' +
            ", teacherPan='" + teacherPan + '\'' +
            '}';
    }
}
