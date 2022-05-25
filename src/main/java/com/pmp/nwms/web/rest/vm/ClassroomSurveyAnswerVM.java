package com.pmp.nwms.web.rest.vm;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class ClassroomSurveyAnswerVM implements Serializable {

    @Size(max = 500)
    private String answerText;
    private Long selectedOptionId;
    @NotNull
    private Long classroomSurveyId;

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public Long getSelectedOptionId() {
        return selectedOptionId;
    }

    public void setSelectedOptionId(Long selectedOptionId) {
        this.selectedOptionId = selectedOptionId;
    }

    public Long getClassroomSurveyId() {
        return classroomSurveyId;
    }

    public void setClassroomSurveyId(Long classroomSurveyId) {
        this.classroomSurveyId = classroomSurveyId;
    }
}
