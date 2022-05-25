package com.pmp.nwms.model;

import java.io.Serializable;
import java.util.Date;

public interface OpenParticipantModel extends Serializable {
    Date getSessionEndDate();

    Long getRubruSessionParticipantId();

    Date getParticipantJoinDateTime();
}
