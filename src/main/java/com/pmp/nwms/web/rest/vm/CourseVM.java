package com.pmp.nwms.web.rest.vm;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pmp.nwms.config.Constants;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

public class CourseVM {
    private Long id;
    @NotNull
    @Size(min = 1, max = 50)
    private String title;
    @Size(min = 0, max = 255)
    private String description;
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_TIME_PATTERN, timezone = Constants.SYSTEM_TIME_ZONE)
    private Date startDateTime;
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.DATE_TIME_PATTERN, timezone = Constants.SYSTEM_TIME_ZONE)
    private Date finishDateTime;


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

    public Date getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(Date startDateTime) {
        this.startDateTime = startDateTime;
    }

    public Date getFinishDateTime() {
        return finishDateTime;
    }

    public void setFinishDateTime(Date finishDateTime) {
        this.finishDateTime = finishDateTime;
    }
}
