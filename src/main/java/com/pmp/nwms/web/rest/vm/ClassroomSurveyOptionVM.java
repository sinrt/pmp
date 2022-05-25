package com.pmp.nwms.web.rest.vm;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class ClassroomSurveyOptionVM implements Serializable {
    private Long id;
    @NotNull
    @Size(min = 3, max = 500)
    private String optionText;
    @NotNull
    @Min(1)
    private Integer optionOrder;

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
}
