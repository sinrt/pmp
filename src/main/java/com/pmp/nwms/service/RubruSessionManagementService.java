package com.pmp.nwms.service;

public interface RubruSessionManagementService {
    void sendPublishSignal(String sessionId, Long userId);

    void sendPublishSignal(String sessionId, String token);

    void sendUnpublishSignal(String sessionId, Long userId);

    void sendUnpublishSignal(String sessionId, String token);

    void closeSession(String sessionId);
}
