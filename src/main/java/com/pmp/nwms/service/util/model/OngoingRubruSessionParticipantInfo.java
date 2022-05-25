package com.pmp.nwms.service.util.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class OngoingRubruSessionParticipantInfo implements Serializable {
    private String participantId;
    private String rubruWsUrl;

    public OngoingRubruSessionParticipantInfo(String participantId, String rubruWsUrl) {
        this.participantId = participantId;
        this.rubruWsUrl = rubruWsUrl;
    }

    public String getParticipantId() {
        return participantId;
    }

    public String getRubruWsUrl() {
        return rubruWsUrl;
    }
}
