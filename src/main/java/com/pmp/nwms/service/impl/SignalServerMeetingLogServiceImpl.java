package com.pmp.nwms.service.impl;

import com.pmp.nwms.domain.Classroom;
import com.pmp.nwms.repository.UserRepository;
import com.pmp.nwms.service.SignalServerLogService;
import com.pmp.nwms.service.SignalServerMeetingLogService;
import com.pmp.nwms.domain.SignalServerMeetingLog;
import com.pmp.nwms.repository.SignalServerMeetingLogRepository;
import com.pmp.nwms.service.dto.SignalServerLogDTO;
import com.pmp.nwms.service.dto.SignalServerMeetingLogDTO;
import com.pmp.nwms.service.mapper.SignalServerMeetingLogMapper;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.Charset;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Service Implementation for managing SignalServerMeetingLog.
 */
@Service
@Transactional
public class SignalServerMeetingLogServiceImpl implements SignalServerMeetingLogService {

    private final Logger log = LoggerFactory.getLogger(SignalServerMeetingLogServiceImpl.class);

    private final SignalServerMeetingLogRepository signalServerMeetingLogRepository;

    private final SignalServerMeetingLogMapper signalServerMeetingLogMapper;

    private final SignalServerLogService signalServerLogService;

    private final ClassroomServiceImpl classroomService;

    static Integer in = 0;

    public SignalServerMeetingLogServiceImpl(SignalServerMeetingLogRepository signalServerMeetingLogRepository, SignalServerMeetingLogMapper signalServerMeetingLogMapper, SignalServerLogServiceImpl signalServerLogService, UserRepository userRepository, ClassroomServiceImpl classroomService) {
        this.signalServerMeetingLogRepository = signalServerMeetingLogRepository;
        this.signalServerMeetingLogMapper = signalServerMeetingLogMapper;
        this.signalServerLogService = signalServerLogService;
        this.classroomService = classroomService;
    }

    /**
     * Save a signalServerMeetingLog.
     *
     * @param signalServerMeetingLogDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public SignalServerMeetingLogDTO save(SignalServerMeetingLogDTO signalServerMeetingLogDTO) {
        log.debug("Request to save SignalServerMeetingLog : {}", signalServerMeetingLogDTO);
        SignalServerMeetingLog signalServerMeetingLog = signalServerMeetingLogMapper.toEntity(signalServerMeetingLogDTO);
        signalServerMeetingLog = signalServerMeetingLogRepository.save(signalServerMeetingLog);
        return signalServerMeetingLogMapper.toDto(signalServerMeetingLog);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<List<SignalServerMeetingLogDTO>> getIncompleteSSMeetingLog(ZonedDateTime zonedDateTime) {
        return signalServerMeetingLogRepository.getIncompleteSSMeetingLog(zonedDateTime).map(signalServerMeetingLogMapper::toDto);
    }

    /**
     * Get all the signalServerMeetingLogs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional
    public Page<SignalServerMeetingLogDTO> findAll(Pageable pageable, boolean isupdate) {
        log.debug("Request to get all SignalServerMeetingLogs");

        if (isupdate) {
            ZonedDateTime zonedDateTime = ZonedDateTime.now().plusDays(-1);
            Optional<List<SignalServerMeetingLogDTO>> signalServerMeetingLogDTOS = getIncompleteSSMeetingLog(zonedDateTime);

            checkIncompleteSSMeetingLog(signalServerMeetingLogDTOS);

            Page<SignalServerLogDTO> signalServerLogDTOS = signalServerLogService.updateSSMeetingRow();

            signalServerLogDTOS.forEach(signalServerLogDTO -> {
                try {
                    createSignalServerMeetingLog(signalServerLogDTO);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            });
            signalServerLogService.setAllCheckToTrue();
        }
        return signalServerMeetingLogRepository.findAll(pageable)
            .map(signalServerMeetingLogMapper::toDto);
    }

    private void checkIncompleteSSMeetingLog(Optional<List<SignalServerMeetingLogDTO>> signalServerMeetingLogDTOS) {

        signalServerMeetingLogDTOS.ifPresent(list -> {
            list.forEach(item -> {
                System.out.println(in++);
                Long id = Long.valueOf(item.getSignalServerLogIds().replace(",", ""));
                Optional<List<SignalServerLogDTO>> signalServerLogDTOS =
                    signalServerLogService.findSessionDestroy("\"sessionId\":\"" + item.getSessionName() + "\"", id);

                if (signalServerLogDTOS.isPresent() &&
                    signalServerLogDTOS.get().get(0).getDescription().contains("sessionDestroyed")) {

                    item.setSignalServerLogIds(item.getSignalServerLogIds() + Long.toString(signalServerLogDTOS.get().get(0).getId()));

                    JSONParser parser = new JSONParser();
                    JSONObject tempJson;
                    try {
                        tempJson = (JSONObject) parser.parse(signalServerLogDTOS.get().get(0).getDescription());
                        tempJson = (JSONObject) tempJson.get("sessionDestroyed");

                        item.setIncompleteStatus(false);
                        ZonedDateTime finishDate = ZonedDateTime.ofInstant(Instant.ofEpochMilli((Long) tempJson.get("timestamp")),
                            ZoneId.systemDefault());
                        item.setSessionFinishDate(finishDate);
                        item.setDuration((Long) tempJson.get("duration"));
                        item.setReason((String) tempJson.get("reason"));
                        signalServerMeetingLogRepository.save(signalServerMeetingLogMapper.toEntity(item));

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

            });
        });

    }


    public void createSignalServerMeetingLog(SignalServerLogDTO signalServerLogDTO) throws ParseException {
        JSONParser parser = new JSONParser();
        JSONObject tempJson;
        String startFinishIds;
        startFinishIds = signalServerLogDTO.getId().toString() + ",";
        SignalServerMeetingLog signalServerMeetingLog = new SignalServerMeetingLog();

        tempJson = (JSONObject) parser.parse(signalServerLogDTO.getDescription());
        tempJson = (JSONObject) tempJson.get("sessionCreated");
        String sessionName = (String) tempJson.get("sessionId");

        ZonedDateTime startDate = ZonedDateTime.ofInstant(Instant.ofEpochMilli((Long) tempJson.get("timestamp")),
            ZoneId.systemDefault());

        Charset u8 = Charset.forName("UTF-8");
        Charset l1 = Charset.forName("ISO-8859-1");
        String utf8String = u8.decode(l1.encode(sessionName)).toString();


        Classroom classroom = classroomService.findClassroomByName(utf8String);

        Optional<List<SignalServerLogDTO>> signalServerLogDTOS =
            signalServerLogService.findSessionDestroy("\"sessionId\":\"" + sessionName + "\"", signalServerLogDTO.getId());

        if (signalServerLogDTOS.isPresent() &&
            signalServerLogDTOS.get().get(0).getDescription().contains("sessionDestroyed")) {
            tempJson = (JSONObject) parser.parse(signalServerLogDTOS.get().get(0).getDescription());
            tempJson = (JSONObject) tempJson.get("sessionDestroyed");
            startFinishIds += signalServerLogDTOS.get().get(0).getId().toString();
            signalServerMeetingLog.setIncompleteStatus(false);
            ZonedDateTime finishDate = ZonedDateTime.ofInstant(Instant.ofEpochMilli((Long) tempJson.get("timestamp")),
                ZoneId.systemDefault());
            signalServerMeetingLog.setSessionFinishDate(finishDate);
            signalServerMeetingLog.setDuration((Long) tempJson.get("duration"));
            signalServerMeetingLog.setReason((String) tempJson.get("reason"));
        } else {
            signalServerMeetingLog.setIncompleteStatus(true);
        }
        signalServerMeetingLog.setSessionStartDate(startDate);
        signalServerMeetingLog.sessionName(sessionName);
        if (classroom != null) {
            signalServerMeetingLog.setCreatorUserName(classroom.getCreator().getLogin());
            signalServerMeetingLog.setCreatorName(classroom.getCreator().getFirstName());
            signalServerMeetingLog.setCreatorID(classroom.getCreator().getId());
            signalServerMeetingLog.setCreatorFamily(classroom.getCreator().getLastName());
        }
        signalServerMeetingLog.setSignalServerLogIds(startFinishIds);
        signalServerMeetingLog = signalServerMeetingLogRepository.save(signalServerMeetingLog);
    }


    /**
     * Get one signalServerMeetingLog by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SignalServerMeetingLogDTO> findOne(Long id) {
        log.debug("Request to get SignalServerMeetingLog : {}", id);
        return signalServerMeetingLogRepository.findById(id)
            .map(signalServerMeetingLogMapper::toDto);
    }

    /**
     * Delete the signalServerMeetingLog by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SignalServerMeetingLog : {}", id);
        signalServerMeetingLogRepository.deleteById(id);
    }
}
