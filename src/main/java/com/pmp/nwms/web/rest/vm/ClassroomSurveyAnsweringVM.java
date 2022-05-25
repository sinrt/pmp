package com.pmp.nwms.web.rest.vm;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

public class ClassroomSurveyAnsweringVM implements Serializable {
    @NotNull
    @Size(min = 1, max = 50)
    private String clientId;

    @Size(max = 200)
    private String token;

    @NotNull
    @Size(min = 1)
    @Valid
    private List<ClassroomSurveyAnswerVM> answers;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<ClassroomSurveyAnswerVM> getAnswers() {
        return answers;
    }

    public void setAnswers(List<ClassroomSurveyAnswerVM> answers) {
        this.answers = answers;
    }
}
