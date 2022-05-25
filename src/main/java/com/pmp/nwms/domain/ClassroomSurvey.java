package com.pmp.nwms.domain;

import com.pmp.nwms.domain.enums.SurveyStatus;
import com.pmp.nwms.domain.enums.SurveyType;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "classroom_survey")
public class ClassroomSurvey extends AbstractAuditingEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "question", length = 500, nullable = false)
    private String question;

    @Enumerated(EnumType.STRING)
    @Column(name = "survey_type", length = 15, nullable = false)
    private SurveyType surveyType;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 9, nullable = false)
    private SurveyStatus status;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "classroom_id")
    private Classroom classroom;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Classroom getClassroom() {
        return classroom;
    }

    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }

    @Override
    public Long fetchPrimaryKey() {
        return getId();
    }
}
