package com.pmp.nwms.web.rest.vm;

import com.pmp.nwms.domain.enums.VisibilityStatus;

import javax.validation.constraints.NotNull;

public class ClassroomFileVM {
    @NotNull
    private Long id;
    @NotNull
    private VisibilityStatus newStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public VisibilityStatus getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(VisibilityStatus newStatus) {
        this.newStatus = newStatus;
    }
}
