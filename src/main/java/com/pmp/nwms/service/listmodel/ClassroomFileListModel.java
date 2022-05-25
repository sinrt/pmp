package com.pmp.nwms.service.listmodel;

import com.pmp.nwms.domain.enums.VisibilityStatus;

import java.io.Serializable;

public interface ClassroomFileListModel extends Serializable {
    Long getId();

    String getFilename();

    String getContentType();

    VisibilityStatus getStatus();
}
