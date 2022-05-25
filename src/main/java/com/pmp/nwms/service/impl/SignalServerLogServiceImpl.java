package com.pmp.nwms.service.impl;

import com.pmp.nwms.service.SignalServerLogService;
import com.pmp.nwms.domain.SignalServerLog;
import com.pmp.nwms.repository.SignalServerLogRepository;
import com.pmp.nwms.service.dto.SignalServerLogDTO;
import com.pmp.nwms.service.mapper.SignalServerLogMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing SignalServerLog.
 */
@Service("signalServerLogService")
@Transactional
public class SignalServerLogServiceImpl implements SignalServerLogService {

    private final Logger log = LoggerFactory.getLogger(SignalServerLogServiceImpl.class);

    private final SignalServerLogRepository signalServerLogRepository;

    private final SignalServerLogMapper signalServerLogMapper;

    public SignalServerLogServiceImpl(SignalServerLogRepository signalServerLogRepository, SignalServerLogMapper signalServerLogMapper) {
        this.signalServerLogRepository = signalServerLogRepository;
        this.signalServerLogMapper = signalServerLogMapper;
    }

    /**
     * Save a signalServerLog.
     *
     * @param signalServerLogDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public SignalServerLogDTO save(SignalServerLogDTO signalServerLogDTO) {
        log.debug("Request to save SignalServerLog : {}", signalServerLogDTO);
        SignalServerLog signalServerLog = signalServerLogMapper.toEntity(signalServerLogDTO);
        signalServerLog = signalServerLogRepository.save(signalServerLog);
        return signalServerLogMapper.toDto(signalServerLog);
    }

    /**
     * Get all the signalServerLogs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SignalServerLogDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SignalServerLogs");
        return signalServerLogRepository.findAll(pageable)
            .map(signalServerLogMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SignalServerLogDTO> updateSSMeetingRow() {
        Pageable wholePage = Pageable.unpaged();
        return signalServerLogRepository.findAllNewSessions(wholePage)
            .map(signalServerLogMapper::toDto);
    }

    @Override
    @Transactional
    public void setAllCheckToTrue() {
        Pageable wholePage = Pageable.unpaged();
        signalServerLogRepository.setAllCheckToTrue();
    }


    /**
     * Get one signalServerLog by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SignalServerLogDTO> findOne(Long id) {
        log.debug("Request to get SignalServerLog : {}", id);
        return signalServerLogRepository.findById(id)
            .map(signalServerLogMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<List<SignalServerLogDTO>> findSessionDestroy(String sessionName, Long id) {
        log.debug("Request to get SignalServerLog : {}", id);
        return signalServerLogRepository.findSessionDestroy(sessionName, id).map(signalServerLogMapper::toDto);
    }

    /**
     * Delete the signalServerLog by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SignalServerLog : {}", id);
        signalServerLogRepository.deleteById(id);
    }
}
