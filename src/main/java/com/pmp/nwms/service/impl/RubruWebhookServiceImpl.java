package com.pmp.nwms.service.impl;

import com.pmp.nwms.NwmsConfig;
import com.pmp.nwms.config.Constants;
import com.pmp.nwms.domain.*;
import com.pmp.nwms.domain.enums.RecordingStorageStatus;
import com.pmp.nwms.repository.*;
import com.pmp.nwms.service.RubruWebhookService;
import com.pmp.nwms.service.dto.ClassroomCheckStatusDTO;
import com.pmp.nwms.service.util.ClassroomStatusDataHolder;
import com.pmp.nwms.service.util.EndSessionTimerHolder;
import com.pmp.nwms.service.util.OngoingRubruSessionDataHolder;
import com.pmp.nwms.web.rest.errors.ClassroomRecordingInfoNotFoundException;
import com.pmp.nwms.web.rest.errors.NoActiveSessionFoundByIdException;
import com.pmp.nwms.web.rest.errors.SessionParticipantNotFoundByIdException;
import com.pmp.nwms.web.rest.vm.rubru.RubruEventVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class RubruWebhookServiceImpl implements RubruWebhookService {

    private static final Logger log = LoggerFactory.getLogger(RubruWebhookServiceImpl.class);

    @Autowired
    private NwmsConfig nwmsConfig;
    @Autowired
    private RubruSessionRepository rubruSessionRepository;
    @Autowired
    private RubruSessionParticipantRepository rubruSessionParticipantRepository;
    @Autowired
    private ClassroomBlockedClientRepository classroomBlockedClientRepository;
    @Autowired
    private ClassroomRecordingInfoRepository classroomRecordingInfoRepository;
    @Autowired
    private ClassroomRepository classroomRepository;
    @Autowired
    private ClassroomStudentRepository classroomStudentRepository;
    @Autowired
    private ClassroomStatusDataHolder classroomStatusDataHolder;
    @Autowired
    private EndSessionTimerHolder endSessionTimerHolder;
    @Autowired
    private OngoingRubruSessionDataHolder ongoingRubruSessionDataHolder;


    @Override
    @Transactional
    public void saveNewEvent(RubruEventVM eventVM) {
        switch (eventVM.getEvent()) {
            case "sessionCreated":
                createNewSession(eventVM);
                break;
            case "sessionDestroyed":
                endSession(eventVM);
                break;
            case "participantJoined":
                addNewParticipantToSession(eventVM);
                break;
            case "participantLeft":
                updateParticipantSessionLeaveInfo(eventVM);
                break;
            case "recordingStatusChanged":
                saveRecordingStatusChanged(eventVM);
                break;
        }
    }

    private void createNewSession(RubruEventVM eventVM) {
        log.info("event " + eventVM.getEvent() + " for session " + eventVM.getSessionId() + " happened at " + eventVM.getTimestamp());

        ClassroomCheckStatusDTO classroom = findClassroom(eventVM.getSessionId());


        UrlInfo urlInfo = makeUrlInfo(classroom, eventVM.getSessionId());
        String appWebUrl = urlInfo.getAppWebUrl();
        String rubruWsUrl = urlInfo.getRubruWsUrl();

        List<Long> openIds = rubruSessionRepository.getOpenSessionIds(eventVM.getSessionId(), rubruWsUrl, appWebUrl);
        if (openIds != null && !openIds.isEmpty()) {
            log.warn(openIds.size() + " sessions by id " + eventVM.getSessionId() + " are open. going to force close them!");
            Date closeDate = new Date(eventVM.getTimestamp() - 1000);
            rubruSessionRepository.closeAllOpenSessions(openIds, closeDate, "forceClose");
            rubruSessionParticipantRepository.closeAllOpenParticipantsForRubruSessionIds(openIds, closeDate, "forceClose");
            classroomRecordingInfoRepository.closeAllOpenClassroomRecordings(openIds, RecordingStatus.forceStop.name(), Arrays.asList(RecordingStatus.started.name(), RecordingStatus.starting.name()));
        }
        RubruSession rubruSession = new RubruSession();
        rubruSession.setSessionId(eventVM.getSessionId());

        if (classroom != null) {
            rubruSession.setClassroomId(classroom.getId());
            Long creatorId = classroom.getCreatorId();
            rubruSession.setCreatorId(creatorId);
            rubruSession.setPurchaseStatusId(urlInfo.getPurchaseStatusId());
        }


        rubruSession.setAppUrl(appWebUrl);
        rubruSession.setOvwsUrl(rubruWsUrl);

        Date startDate = new Date(eventVM.getTimestamp());
        rubruSession.setStartDate(startDate);
        rubruSession.setSessionName(classroom.getName());
        rubruSession = rubruSessionRepository.save(rubruSession);

        classroomRepository.updateSessionActiveStatus(classroom.getId(), true);
        classroomStatusDataHolder.removeClassroom(eventVM.getSessionId());
        endSessionTimerHolder.addSessionTimer(eventVM.getSessionId(), classroom.getFinishDateTime(), classroom.isSignalSessionEnd(), rubruWsUrl);
        if (classroom.isOuterManage()) {
            ongoingRubruSessionDataHolder.startNewSession(eventVM.getSessionId(), rubruWsUrl);
        }
    }

    private ClassroomCheckStatusDTO findClassroom(String sessionId) {
        return classroomStatusDataHolder.findByUuid(sessionId);
    }

    private void endSession(RubruEventVM eventVM) {
        Long duration = Long.valueOf(eventVM.getDuration());
        Date endDate = new Date(eventVM.getStartTime() + (duration * 1000));
        log.info("session by id " + eventVM.getSessionId() + " ended at " + endDate);
        RubruSession rubruSession = findActiveSession(eventVM.getSessionId(), new Date(eventVM.getStartTime() + (duration * 500)));
        rubruSession.setDuration(duration);
        rubruSession.setEndDate(endDate);
        rubruSession.setEndReason(eventVM.getReason());
        final RubruSession rubruSession2 = rubruSessionRepository.save(rubruSession);

        classroomBlockedClientRepository.deleteByClassroomId(rubruSession2.getClassroomId());

        Thread t = new Thread(() -> {
            List<RubruSessionParticipant> participants = rubruSessionParticipantRepository.findOpenParticipantsUsingRubruSessionId(rubruSession2.getId());
            if (participants != null && !participants.isEmpty()) {
                for (RubruSessionParticipant participant : participants) {
                    participant.setLeaveReason("forceCloseUnclosed");
                    participant.setLeaveDateTime(rubruSession2.getEndDate());
                    participant.setDuration((rubruSession2.getEndDate().getTime() - participant.getJoinDateTime().getTime()) / 1000L);
                    participant = rubruSessionParticipantRepository.save(participant);
                }
            }
        });

        t.start();

        classroomRepository.updateSessionActiveStatus(rubruSession2.getClassroomId(), false);
        classroomStatusDataHolder.removeClassroom(eventVM.getSessionId());
        endSessionTimerHolder.cancelSessionTimer(eventVM.getSessionId());
        ongoingRubruSessionDataHolder.endSession(eventVM.getSessionId());
    }

    private void addNewParticipantToSession(RubruEventVM eventVM) {
        log.info("addNewParticipantToSession started for participant " + eventVM.getParticipantId() + " and session " + eventVM.getSessionId());
        RubruSessionParticipant rubruSessionParticipant = new RubruSessionParticipant();
        String clientData = eventVM.getClientData();
        Map<String, String> clientDataMap = readClientData(clientData);

        boolean skipParticipant = checkParticipant(clientDataMap);

        if (skipParticipant) {
            log.info("Participant by id " + eventVM.getParticipantId() + " in session " + eventVM.getSessionId() + " is " + clientDataMap.get(ClientDataFieldNames.connectionType.name()) + " participant. going to skip saving its join info");
            return;
        }

        String participantName = clientDataMap.get("clientData");
        if (participantName == null || participantName.isEmpty()) {
            participantName = "UNDEFINED";
        }
        rubruSessionParticipant.setParticipantName(participantName);
        if (clientDataMap.containsKey("participantKey")) {
            rubruSessionParticipant.setParticipantKey(clientDataMap.get("participantKey"));
        }
        rubruSessionParticipant.setClientId(clientDataMap.get("client_id"));
        rubruSessionParticipant.setUserToken(clientDataMap.get("userToken"));
        String userId = clientDataMap.get("userId");
        Date joinDateTime = new Date(eventVM.getTimestamp());
        log.info("participant by id " + eventVM.getParticipantId() + " is joining session " + eventVM.getSessionId() + " at " + joinDateTime);
        RubruSession rubruSession = findActiveSession(eventVM.getSessionId(), new Date());
        if (userId == null || userId.isEmpty() || userId.trim().isEmpty()) {
            if(rubruSessionParticipant.getUserToken() != null && !rubruSessionParticipant.getUserToken().isEmpty() && !rubruSessionParticipant.getUserToken().trim().isEmpty()){
                Long studentId = classroomStudentRepository.findStudentIdUsingToken(rubruSessionParticipant.getUserToken().trim());
                if(studentId != null){
                    userId = studentId.toString();
                }
            }
        }
        if (userId != null && !userId.isEmpty() && !userId.trim().isEmpty()) {
            try {
                Long userIdValue = Long.valueOf(userId.trim());
                rubruSessionParticipant.setUserId(userIdValue);
                Optional<ClassroomStudent> classroomStudent = classroomStudentRepository.findByClassroomAndStudentId(rubruSession.getClassroomId(), userIdValue);
                if (classroomStudent.isPresent()) {
                    rubruSessionParticipant.setParticipantRole(classroomStudent.get().getAuthorityName());
                }

            } catch (NumberFormatException e) {
                log.warn("NumberFormatException in converting userId : " + userId + " setd clientData is " + clientData);
            }
        }


        long count = rubruSessionParticipantRepository.countOpenParticipants(eventVM.getParticipantId(), rubruSession.getId());
        if (count > 0) {
            Date closeDate = new Date(eventVM.getTimestamp() - 1000);
            rubruSessionParticipantRepository.closeAllOpenParticipants(eventVM.getParticipantId(), rubruSession.getId(), closeDate, "forceClose");
        }

        rubruSessionParticipant.setParticipantType(eventVM.getRole());
        rubruSessionParticipant.setRubruSession(rubruSession);
        rubruSessionParticipant.setParticipantId(eventVM.getParticipantId());
        rubruSessionParticipant.setLocation(eventVM.getLocation());
        rubruSessionParticipant.setPlatform(eventVM.getPlatform());

        rubruSessionParticipant.setServerData(eventVM.getServerData());
        rubruSessionParticipant.setJoinDateTime(joinDateTime);

        rubruSessionParticipant = rubruSessionParticipantRepository.save(rubruSessionParticipant);
        ongoingRubruSessionDataHolder.addNewParticipant(eventVM.getSessionId(), rubruSessionParticipant.getUserId(), rubruSessionParticipant.getUserToken(), rubruSessionParticipant.getParticipantId());
    }

    private boolean checkParticipant(Map<String, String> clientDataMap) {
        if (clientDataMap != null && !clientDataMap.isEmpty()) {
            String connectionType = clientDataMap.get(ClientDataFieldNames.connectionType.name());
            return !connectionType.equalsIgnoreCase(ConnectionTypes.main.name());
        }
        return false;
    }

    private Map<String, String> readClientData(String clientData) {
        if (clientData != null && !clientData.isEmpty()) {

            String clientDataTemp = clientData.replace("{\"", "")
                .replace("\":\"\"", ":NULL\"")
                .replace("\":\"", ":")
                .replace("\":", ":")
                .replace("\",\"", "$$$")
                .replace("\"}", "")
                .replace("}", "");

            String[] s1 = clientDataTemp.split("[$][$][$]");
            Map<String, String> map = new HashMap<>();

            for (String s : s1) {
                String key = s.substring(0, s.indexOf(":"));
                String value = s.replace(key + ":", "");
                if (key.equalsIgnoreCase("clientData") && !value.matches(Constants.PARTICIPANT_NAME_REGEX)) {
                    value = value.replaceAll(Constants.NOT_PARTICIPANT_NAME_REGEX, "_");
                }
                if (value.equalsIgnoreCase("NULL")) {
                    value = "";
                }
                map.put(key, value);
            }
            return map;
        }
        return null;
    }

    private void updateParticipantSessionLeaveInfo(RubruEventVM eventVM) {
        log.info("updateParticipantSessionLeaveInfo started for participant " + eventVM.getParticipantId() + " and session " + eventVM.getSessionId());
        String clientData = eventVM.getClientData();
        Map<String, String> clientDataMap = readClientData(clientData);

        boolean skipParticipant = checkParticipant(clientDataMap);
        if (skipParticipant) {
            log.info("Participant by id " + eventVM.getParticipantId() + " in session " + eventVM.getSessionId() + " is " + clientDataMap.get(ClientDataFieldNames.connectionType.name()) + " participant. going to skip saving its leave info");
            return;
        }
        Long duration = Long.valueOf(eventVM.getDuration());
        Date leaveDateTime = new Date(eventVM.getStartTime() + (duration * 1000));
        log.info("Participant by id " + eventVM.getParticipantId() + " is leaving session " + eventVM.getSessionId() + " at " + leaveDateTime);
//        RubruSession rubruSession = findActiveSession(eventVM.getSessionId(), new Date(eventVM.getStartTime()));
        Optional<RubruSessionParticipant> optional = rubruSessionParticipantRepository.findByParticipantId(eventVM.getParticipantId());
        if (optional.isPresent()) {
            RubruSessionParticipant rubruSessionParticipant = optional.get();
            rubruSessionParticipant.setJoinDateTime(new Date(eventVM.getStartTime()));

            rubruSessionParticipant.setLeaveDateTime(leaveDateTime);
            rubruSessionParticipant.setDuration(duration);
            rubruSessionParticipant.setLeaveReason(eventVM.getReason());
            rubruSessionParticipant = rubruSessionParticipantRepository.save(rubruSessionParticipant);
            ongoingRubruSessionDataHolder.removeParticipant(eventVM.getSessionId(), rubruSessionParticipant.getUserId(), rubruSessionParticipant.getUserToken());
        } else {
            throw new SessionParticipantNotFoundByIdException(eventVM.getSessionId(), null, eventVM.getParticipantId());//todo?
        }
    }

    private void saveRecordingStatusChanged(RubruEventVM eventVM) {
        RecordingStatus status = RecordingStatus.valueOf(eventVM.getStatus());
        ClassroomCheckStatusDTO classroom = findClassroom(eventVM.getSessionId());
        UrlInfo urlInfo = makeUrlInfo(classroom, eventVM.getSessionId());
        switch (status) {
            case starting:
                log.warn("recording for session " + eventVM.getSessionId() + " sent by status starting, going to ignore it.");
                break;
            case started: {
                ClassroomRecordingInfo classroomRecordingInfo = new ClassroomRecordingInfo();
                classroomRecordingInfo.setRubruSessionName(eventVM.getSessionId());
                try {
                    RubruSession rubruSession = findActiveSession(eventVM.getSessionId(), new Date());
                    classroomRecordingInfo.setRubruSessionId(rubruSession.getId());
                } catch (NoActiveSessionFoundByIdException e) {
                    log.warn("active rubruSession not found for " + eventVM.getSessionId());
                }
                classroomRecordingInfo.setRecordingId(eventVM.getId());
                classroomRecordingInfo.setRecordingName(eventVM.getName());
                classroomRecordingInfo.setOutputMode(eventVM.getOutputMode());
                classroomRecordingInfo.setResolution(eventVM.getResolution());
                classroomRecordingInfo.setRecordingLayout(eventVM.getRecordingLayout());
                classroomRecordingInfo.setCustomLayout(eventVM.getCustomLayout());
                classroomRecordingInfo.setHasAudio(eventVM.getHasAudio());
                classroomRecordingInfo.setHasVideo(eventVM.getHasVideo());
                classroomRecordingInfo.setFileSize(eventVM.getSize());
                classroomRecordingInfo.setStatus(eventVM.getStatus());
                classroomRecordingInfo.setDuration(Double.valueOf(eventVM.getDuration()));
                classroomRecordingInfo.setReason(eventVM.getReason());
                classroomRecordingInfo.setStartTime(eventVM.getStartTime());
                classroomRecordingInfo.setStartTimeStamp(eventVM.getTimestamp());
                classroomRecordingInfo.setDownloadBaseUrl(urlInfo.rubruWsUrl);
                classroomRecordingInfo.setStorageStatus(RecordingStorageStatus.WaitForStop);
                classroomRecordingInfo.setClassroomId(classroom.getId());
                classroomRecordingInfo = classroomRecordingInfoRepository.save(classroomRecordingInfo);
                break;
            }
            case stopped: {
                //todo what about the forceStop
                List<ClassroomRecordingInfo> op = classroomRecordingInfoRepository.findByClassroomIdAndRecordingIdAndDownloadBaseUrlInStatuses(classroom.getId(), eventVM.getId(), urlInfo.rubruWsUrl, Arrays.asList(RecordingStatus.started.name(), RecordingStatus.ready.name(), RecordingStatus.forceStop.name()));
                if (op == null || op.isEmpty()) {
                    log.error("ClassroomRecordingInfoNotFoundException classroomId : [" + classroom.getId() + "], recordingId : [" + eventVM.getId() + "], downloadBaseUrl : [" + urlInfo.rubruWsUrl + "]");
                    throw new ClassroomRecordingInfoNotFoundException(classroom.getId(), eventVM.getId(), urlInfo.rubruWsUrl);
                }
                ClassroomRecordingInfo classroomRecordingInfo = op.get(0);
                if (classroomRecordingInfo.getStorageStatus().equals(RecordingStorageStatus.WaitForStop)) {
                    classroomRecordingInfo.setStorageStatus(RecordingStorageStatus.WaitForFinalize);
                }
                if (classroomRecordingInfo.getStatus().equals(RecordingStatus.started.name())) {
                    classroomRecordingInfo.setStatus(eventVM.getStatus());
                    classroomRecordingInfo.setDuration(Double.valueOf(eventVM.getDuration()));
                }
                classroomRecordingInfo.setReason(eventVM.getReason());
                classroomRecordingInfo.setStopTimeStamp(eventVM.getTimestamp());
                classroomRecordingInfo = classroomRecordingInfoRepository.save(classroomRecordingInfo);
                break;
            }
            case ready: {
                //todo what about the forceStop
                List<ClassroomRecordingInfo> op = classroomRecordingInfoRepository.findByClassroomIdAndRecordingIdAndDownloadBaseUrlInStatuses(classroom.getId(), eventVM.getId(), urlInfo.rubruWsUrl, Arrays.asList(RecordingStatus.started.name(), RecordingStatus.stopped.name(), RecordingStatus.forceStop.name()));
                if (op == null || op.isEmpty()) {
                    log.error("ClassroomRecordingInfoNotFoundException classroomId : [" + classroom.getId() + "], recordingId : [" + eventVM.getId() + "], downloadBaseUrl : [" + urlInfo.rubruWsUrl + "]");
                    throw new ClassroomRecordingInfoNotFoundException(classroom.getId(), eventVM.getId(), urlInfo.rubruWsUrl);
                }
                ClassroomRecordingInfo classroomRecordingInfo = op.get(0);
                classroomRecordingInfo.setStatus(eventVM.getStatus());
                classroomRecordingInfo.setDuration(Double.valueOf(eventVM.getDuration()));
                classroomRecordingInfo.setFileSize(eventVM.getSize());
                classroomRecordingInfo.setReason(eventVM.getReason());
                classroomRecordingInfo.setFinalTimeStamp(eventVM.getTimestamp());
                classroomRecordingInfo.setStorageStatus(RecordingStorageStatus.ReadyToDownload);
                classroomRecordingInfo = classroomRecordingInfoRepository.save(classroomRecordingInfo);

                break;
            }
            case failed: {
                List<ClassroomRecordingInfo> op = classroomRecordingInfoRepository.findByClassroomIdAndRecordingIdAndDownloadBaseUrlInStatuses(classroom.getId(), eventVM.getId(), urlInfo.rubruWsUrl, Arrays.asList(RecordingStatus.started.name(), RecordingStatus.stopped.name(), RecordingStatus.forceStop.name()));
                if (op == null || op.isEmpty()) {
                    log.error("ClassroomRecordingInfoNotFoundException classroomId : [" + classroom.getId() + "], recordingId : [" + eventVM.getId() + "], downloadBaseUrl : [" + urlInfo.rubruWsUrl + "]");
                    throw new ClassroomRecordingInfoNotFoundException(classroom.getId(), eventVM.getId(), urlInfo.rubruWsUrl);
                }
                ClassroomRecordingInfo classroomRecordingInfo = op.get(0);
                classroomRecordingInfo.setStatus(eventVM.getStatus());
                classroomRecordingInfo.setDuration(Double.valueOf(eventVM.getDuration()));
                classroomRecordingInfo.setReason(eventVM.getReason());
                classroomRecordingInfo.setFinalTimeStamp(eventVM.getTimestamp());
                classroomRecordingInfo.setStorageStatus(RecordingStorageStatus.FinalizeFailed);
                break;
            }
        }

    }


    private RubruSession findActiveSession(String sessionId, Date date) {
        UrlInfo urlInfo = makeUrlInfo(findClassroom(sessionId), sessionId);
        Optional<RubruSession> optional = rubruSessionRepository.findActiveSession(sessionId, date, urlInfo.getRubruWsUrl(), urlInfo.getAppWebUrl());
        if (optional.isPresent()) {
            return optional.get();
        } else {
            log.error("session by id " + sessionId + " is not open in date " + date);
            throw new NoActiveSessionFoundByIdException(sessionId);
        }
    }

    public static void main(String[] args) {
        String clientData = "{\"clientData\":\"حسین اربابون1\"23\",\"userToken\":\"\",\"userId\":\"\"}";

        String clientDataTemp = clientData.replace("{\"", "")
            .replace("\":\"\"", ":NULL\"")
            .replace("\":\"", ":")
            .replace("\",\"", "$$$")
            .replace("\"}", "");

        String[] s1 = clientDataTemp.split("[$][$][$]");
        Map<String, String> map = new HashMap<>();

        for (int i = 0; i < s1.length; i++) {
            String s = s1[i];
            String key = s.substring(0, s.indexOf(":"));
            String value = s.replace(key + ":", "");
            if (key.equalsIgnoreCase("clientData") && !value.matches(Constants.PARTICIPANT_NAME_REGEX)) {
                value = value.replaceAll(Constants.NOT_PARTICIPANT_NAME_REGEX, "_");
            }
            map.put(key, value);
        }


        System.out.println("map = " + map);
    }

    private UrlInfo makeUrlInfo(ClassroomCheckStatusDTO classroom, String sessionId) {
        String appWebUrl = nwmsConfig.getAppWebUrl();
        String rubruWsUrl = nwmsConfig.getRubruWsUrl();
        Long purchaseStatusId = null;
        if (classroom == null) {
            log.warn("session by id " + sessionId + " is being started, but there is no classroom by this name!");
        } else {
            PurchaseStatus purchaseStatus = classroomStatusDataHolder.findPurchaseStatusByUser(classroom.getCreatorId());
            if (!purchaseStatus.getId().equals(-1L)) {
                purchaseStatusId = purchaseStatus.getId();
                if (purchaseStatus.getAppUrl() != null && !purchaseStatus.getAppUrl().isEmpty()) {
                    appWebUrl = purchaseStatus.getAppUrl();
                }
                if (purchaseStatus.getWsUrl() != null && !purchaseStatus.getWsUrl().isEmpty()) {
                    rubruWsUrl = purchaseStatus.getWsUrl();
                }
            } else {
                log.warn("user by id " + classroom.getCreatorId() + " for session " + sessionId + " has no current plan! but session is starting");
            }
        }
        return new UrlInfo(appWebUrl, rubruWsUrl, purchaseStatusId);
    }


    public enum ClientDataFieldNames {
        clientData,
        connectionType,
        ownerId
    }

    public enum ConnectionTypes {
        main,
        screen,
        slide,
        whiteboard
    }

    public enum RecordingStatus {
        starting,
        started,
        stopped,
        ready,
        failed,
        forceStop;
    }

    public enum NormalSessionEndReasons {
        automaticStop,
        lastParticipantLeft

        ;
        //todo any thing else?!
        public static List<String> names(){
            ArrayList<String> result = new ArrayList<>();
            for (NormalSessionEndReasons value : values()) {
                result.add(value.name());
            }
            return result;
        }
    }

    private class UrlInfo {
        private String appWebUrl;
        private String rubruWsUrl;
        private Long purchaseStatusId;

        public UrlInfo(String appWebUrl, String rubruWsUrl, Long purchaseStatusId) {
            this.appWebUrl = appWebUrl;
            this.rubruWsUrl = rubruWsUrl;
            this.purchaseStatusId = purchaseStatusId;
        }

        public String getAppWebUrl() {
            return appWebUrl;
        }

        public String getRubruWsUrl() {
            return rubruWsUrl;
        }

        public Long getPurchaseStatusId() {
            return purchaseStatusId;
        }
    }

}
