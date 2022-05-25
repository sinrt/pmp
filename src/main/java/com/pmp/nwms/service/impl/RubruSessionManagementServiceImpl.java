package com.pmp.nwms.service.impl;

import com.google.gson.Gson;
import com.pmp.nwms.NwmsConfig;
import com.pmp.nwms.repository.ClassroomRepository;
import com.pmp.nwms.service.RubruSessionManagementService;
import com.pmp.nwms.service.dto.ClassroomCheckStatusDTO;
import com.pmp.nwms.service.util.ClassroomStatusDataHolder;
import com.pmp.nwms.service.util.OngoingRubruSessionDataHolder;
import com.pmp.nwms.service.util.SignalServerRestCallUtil;
import com.pmp.nwms.service.util.model.OngoingRubruSessionParticipantInfo;
import com.pmp.nwms.util.ClassroomUtil;
import com.pmp.nwms.web.rest.errors.ClassroomNotFoundByNameException;
import com.pmp.nwms.web.rest.errors.ClassroomNotOuterManagedException;
import com.pmp.nwms.web.rest.errors.ParticipantNotFoundInSessionException;
import com.pmp.nwms.web.rest.errors.SessionNotStartedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class RubruSessionManagementServiceImpl implements RubruSessionManagementService {
    @Autowired
    private ClassroomRepository classroomRepository;
    @Autowired
    private ClassroomStatusDataHolder classroomStatusDataHolder;
    @Autowired
    private OngoingRubruSessionDataHolder ongoingRubruSessionDataHolder;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private NwmsConfig nwmsConfig;

    @Override
    public void sendPublishSignal(String sessionId, Long userId) {
        validate(sessionId);

        OngoingRubruSessionParticipantInfo participantInfo = ongoingRubruSessionDataHolder.getParticipantInfo(sessionId, userId);
        if (participantInfo == null || participantInfo.getParticipantId() == null || participantInfo.getParticipantId().isEmpty()) {
            throw new ParticipantNotFoundInSessionException();
        }


        sendSignalMessage(sessionId, participantInfo.getRubruWsUrl(), "START_PUBLISHING", participantInfo.getParticipantId());
    }

    @Override
    public void sendPublishSignal(String sessionId, String token) {
        validate(sessionId);

        OngoingRubruSessionParticipantInfo participantInfo = ongoingRubruSessionDataHolder.getParticipantInfo(sessionId, token);
        if (participantInfo == null || participantInfo.getParticipantId() == null || participantInfo.getParticipantId().isEmpty()) {
            throw new ParticipantNotFoundInSessionException();
        }

        sendSignalMessage(sessionId, participantInfo.getRubruWsUrl(), "START_PUBLISHING", participantInfo.getParticipantId());
    }

    @Override
    public void sendUnpublishSignal(String sessionId, Long userId) {
        validate(sessionId);

        OngoingRubruSessionParticipantInfo participantInfo = ongoingRubruSessionDataHolder.getParticipantInfo(sessionId, userId);
        if (participantInfo == null || participantInfo.getParticipantId() == null || participantInfo.getParticipantId().isEmpty()) {
            throw new ParticipantNotFoundInSessionException();
        }

        sendSignalMessage(sessionId, participantInfo.getRubruWsUrl(), "STOP_PUBLISHING", participantInfo.getParticipantId());
    }

    @Override
    public void sendUnpublishSignal(String sessionId, String token) {
        validate(sessionId);

        OngoingRubruSessionParticipantInfo participantInfo = ongoingRubruSessionDataHolder.getParticipantInfo(sessionId, token);
        if (participantInfo == null || participantInfo.getParticipantId() == null || participantInfo.getParticipantId().isEmpty()) {
            throw new ParticipantNotFoundInSessionException();
        }

        sendSignalMessage(sessionId, participantInfo.getRubruWsUrl(), "STOP_PUBLISHING", participantInfo.getParticipantId());
    }

    @Override
    public void closeSession(String sessionId) {
        validate(sessionId);
        OngoingRubruSessionParticipantInfo participantInfo = ongoingRubruSessionDataHolder.getSessionInfo(sessionId);
        HttpHeaders headers = SignalServerRestCallUtil.makeHttpHeaders(MediaType.APPLICATION_FORM_URLENCODED, nwmsConfig);
        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
        String closeSessionUrl = "https://" + participantInfo.getRubruWsUrl() + "/secured-api/sessions/" + sessionId + "/sessionClosedByOuterManager";
        ;
        restTemplate.exchange(closeSessionUrl, HttpMethod.DELETE, requestEntity, Void.class);

    }

    private void validate(String sessionId) {
        ClassroomCheckStatusDTO classroom = classroomStatusDataHolder.findByUuid(sessionId);
        if (classroom == null) {
            throw new ClassroomNotFoundByNameException();
        }
        ClassroomUtil.checkClassroom(classroom.getId(), classroomRepository);
        if (!classroom.isOuterManage()) {
            throw new ClassroomNotOuterManagedException();
        }
        if (!ongoingRubruSessionDataHolder.isSessionOngoing(sessionId)) {
            throw new SessionNotStartedException();
        }
    }


    private void sendSignalMessage(String sessionId, String rubruWsUrl, String type, String participantId) {
        String sendSignalUrl = "https://" + rubruWsUrl + "/secured-api/signal";
        HttpHeaders headers = SignalServerRestCallUtil.makeHttpHeaders(MediaType.APPLICATION_JSON_UTF8, nwmsConfig);
        Gson gson = new Gson();
        Map<String, Object> src = new HashMap<>();
        src.put("session", sessionId);
        src.put("type", type);
        src.put("to", Collections.singletonList(participantId));//todo???
        src.put("data", "");
        String body = gson.toJson(src);
        HttpEntity<String> requestEntity = new HttpEntity<>(body, headers);
        restTemplate.exchange(sendSignalUrl, HttpMethod.POST, requestEntity, Void.class);
    }
}
