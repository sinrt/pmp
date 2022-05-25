package com.pmp.nwms.service.util;

import com.pmp.nwms.service.util.model.OngoingRubruSessionInfo;
import com.pmp.nwms.service.util.model.OngoingRubruSessionParticipantInfo;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component("ongoingRubruSessionDataHolder")
public class OngoingRubruSessionDataHolder {
    private Map<String, OngoingRubruSessionInfo> ongoingSessions = new HashMap<>();

    public void startNewSession(String sessionId, String rubruWsUrl) {
        OngoingRubruSessionInfo sessionInfo = new OngoingRubruSessionInfo(sessionId, rubruWsUrl);
        this.ongoingSessions.put(sessionId, sessionInfo);
    }

    public void endSession(String sessionId) {
        ongoingSessions.remove(sessionId);
    }

    public void addNewParticipant(String sessionId, Long userId, String token, String participantId) {
        if (this.ongoingSessions.containsKey(sessionId)) {
            this.ongoingSessions.get(sessionId).addParticipant(userId, token, participantId);
        }
    }

    public void removeParticipant(String sessionId, Long userId, String token) {
        if (this.ongoingSessions.containsKey(sessionId)) {
            this.ongoingSessions.get(sessionId).removeParticipant(userId, token);
        }

    }


    public boolean isSessionOngoing(String sessionId) {
        return ongoingSessions.containsKey(sessionId);
    }

    public OngoingRubruSessionParticipantInfo getParticipantInfo(String sessionId, Long userId) {
        if (this.ongoingSessions.containsKey(sessionId)) {
            OngoingRubruSessionInfo sessionInfo = this.ongoingSessions.get(sessionId);
            return new OngoingRubruSessionParticipantInfo(sessionInfo.getParticipantId(userId), sessionInfo.getRubruWsUrl());
        }
        return null;
    }

    public OngoingRubruSessionParticipantInfo getParticipantInfo(String sessionId, String token) {
        if (this.ongoingSessions.containsKey(sessionId)) {
            OngoingRubruSessionInfo sessionInfo = this.ongoingSessions.get(sessionId);
            return new OngoingRubruSessionParticipantInfo(sessionInfo.getParticipantId(token), sessionInfo.getRubruWsUrl());
        }
        return null;
    }

    public OngoingRubruSessionParticipantInfo getSessionInfo(String sessionId) {
        if (this.ongoingSessions.containsKey(sessionId)) {
            OngoingRubruSessionInfo sessionInfo = this.ongoingSessions.get(sessionId);
            return new OngoingRubruSessionParticipantInfo(null, sessionInfo.getRubruWsUrl());
        }
        return null;
    }

}
