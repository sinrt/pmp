package com.pmp.nwms.service.util.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class OngoingRubruSessionInfo implements Serializable {
    private String sessionId;
    private String rubruWsUrl;
    private Map<String, String> participantIdsByToken;
    private Map<Long, String> participantIdsById;

    public OngoingRubruSessionInfo(String sessionId, String rubruWsUrl) {
        this.sessionId = sessionId;
        this.rubruWsUrl = rubruWsUrl;
        this.participantIdsByToken = new HashMap<>();
        this.participantIdsById = new HashMap<>();
    }

    public String getRubruWsUrl() {
        return rubruWsUrl;
    }

    public void addParticipant(Long userId, String token, String participantId) {
        if (userId != null) {
            this.participantIdsById.put(userId, participantId);
        }
        if (token != null && !token.isEmpty()) {
            this.participantIdsByToken.put(token, participantId);
        }
    }

    public void removeParticipant(Long userId, String token) {
        if (userId != null) {
            this.participantIdsById.remove(userId);
        }
        if (token != null && !token.isEmpty()) {
            this.participantIdsByToken.remove(token);
        }
    }

    public String getParticipantId(Long userId) {
        return this.participantIdsById.get(userId);
    }

    public String getParticipantId(String token) {
        return this.participantIdsByToken.get(token);
    }
}
