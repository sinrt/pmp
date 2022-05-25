package com.pmp.nwms.service;

import com.pmp.nwms.domain.SignalServerLog;
import com.pmp.nwms.service.dto.SignalServerLogDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing SignalServerLog.
 */
public interface SignalServerLogService {

    /**
     * Save a signalServerLog.
     *
     * @param signalServerLogDTO the entity to save
     * @return the persisted entity
     */
    SignalServerLogDTO save(SignalServerLogDTO signalServerLogDTO);

    /**
     * Get all the signalServerLogs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<SignalServerLogDTO> findAll(Pageable pageable);

    Page<SignalServerLogDTO> updateSSMeetingRow();

    void setAllCheckToTrue();


    /**
     * Get the "id" signalServerLog.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<SignalServerLogDTO> findOne(Long id);


    Optional<List<SignalServerLogDTO>> findSessionDestroy(String sessionName, Long id);

    /**
     * Delete the "id" signalServerLog.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
