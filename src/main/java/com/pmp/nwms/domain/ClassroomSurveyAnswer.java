package com.pmp.nwms.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "classroom_survey_answer")
public class ClassroomSurveyAnswer extends AbstractAuditingEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "answer_text", length = 500, nullable = true)
    private String answerText;

    @Column(name = "user_id", nullable = true)
    private Long userId;

    @Column(name = "client_id", length = 50, nullable = false)
    private String clientId;

    @Column(name = "token", length = 200, nullable = true)
    private String token;

    @Column(name = "answer_date_time", nullable = false)
    private Date answerDateTime;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "classroom_survey_id")
    private ClassroomSurvey classroomSurvey;

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "selected_option_id", nullable = true)
    private ClassroomSurveyOption selectedOption;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

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

    public Date getAnswerDateTime() {
        return answerDateTime;
    }

    public void setAnswerDateTime(Date answerDateTime) {
        this.answerDateTime = answerDateTime;
    }

    public ClassroomSurvey getClassroomSurvey() {
        return classroomSurvey;
    }

    public void setClassroomSurvey(ClassroomSurvey classroomSurvey) {
        this.classroomSurvey = classroomSurvey;
    }

    public ClassroomSurveyOption getSelectedOption() {
        return selectedOption;
    }

    public void setSelectedOption(ClassroomSurveyOption selectedOption) {
        this.selectedOption = selectedOption;
    }

    @Override
    public Long fetchPrimaryKey() {
        return getId();
    }
}
