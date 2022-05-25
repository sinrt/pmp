package com.pmp.nwms.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "classroom_survey_option")
public class ClassroomSurveyOption implements Serializable, BaseEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "option_text", length = 500, nullable = false)
    private String optionText;

    @Column(name = "option_order", nullable = false)
    private Integer optionOrder;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "classroom_survey_id")
    private ClassroomSurvey classroomSurvey;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOptionText() {
        return optionText;
    }

    public void setOptionText(String optionText) {
        this.optionText = optionText;
    }

    public Integer getOptionOrder() {
        return optionOrder;
    }

    public void setOptionOrder(Integer optionOrder) {
        this.optionOrder = optionOrder;
    }

    public ClassroomSurvey getClassroomSurvey() {
        return classroomSurvey;
    }

    public void setClassroomSurvey(ClassroomSurvey classroomSurvey) {
        this.classroomSurvey = classroomSurvey;
    }

    @Override
    public Long fetchPrimaryKey() {
        return getId();
    }

}
