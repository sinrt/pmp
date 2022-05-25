package com.pmp.nwms.service.dto;

import java.io.Serializable;

public interface ClassroomInfoDto extends Serializable {
    Long getId();

    Long getCourseId();

    Long getCourseCreatorId();

    Long getClassroomCreatorId();
}
