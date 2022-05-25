package com.pmp.nwms.web.rest.vm;

import com.pmp.nwms.domain.enums.SurveyType;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

public class ClassroomSurveyVM implements Serializable {
    private Long id;
    @NotNull
    private Long classroomId;
    @NotNull
    @Size(min = 3, max = 500)
    private String question;
    @NotNull
    private SurveyType surveyType;
    @Valid
    private List<ClassroomSurveyOptionVM> options;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClassroomId() {
        return classroomId;
    }

    public void setClassroomId(Long classroomId) {
        this.classroomId = classroomId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public SurveyType getSurveyType() {
        return surveyType;
    }

    public void setSurveyType(SurveyType surveyType) {
        this.surveyType = surveyType;
    }

    public List<ClassroomSurveyOptionVM> getOptions() {
        return options;
    }

    public void setOptionS(List<ClassroomSurveyOptionVM> options) {
        this.options = options;
    }
}
