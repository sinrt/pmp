package com.pmp.nwms.service.dto;

import com.pmp.nwms.domain.enums.SurveyStatus;
import com.pmp.nwms.domain.enums.SurveyType;

import java.io.Serializable;
import java.util.List;

public class ClassroomSurveyDTO implements Serializable {
    private Long id;
    private Long classroomId;
    private String question;
    private SurveyType surveyType;
    private SurveyStatus status;
    private List<ClassroomSurveyOptionDTO> options;

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

    public SurveyStatus getStatus() {
        return status;
    }

    public void setStatus(SurveyStatus status) {
        this.status = status;
    }

    public List<ClassroomSurveyOptionDTO> getOptions() {
        return options;
    }

    public void setOptions(List<ClassroomSurveyOptionDTO> options) {
        this.options = options;
    }
}
