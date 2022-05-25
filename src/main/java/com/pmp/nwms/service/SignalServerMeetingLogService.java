package com.pmp.nwms.service;

import com.pmp.nwms.service.dto.SignalServerMeetingLogDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing SignalServerMeetingLog.
 */
public interface SignalServerMeetingLogService {

    /**
     * Save a signalServerMeetingLog.
     *
     * @param signalServerMeetingLogDTO the entity to save
     * @return the persisted entity
     */
    SignalServerMeetingLogDTO save(SignalServerMeetingLogDTO signalServerMeetingLogDTO);

    /**
     * Get all the signalServerMeetingLogs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<SignalServerMeetingLogDTO> findAll(Pageable pageable,boolean isupdate);

    Optional<List<SignalServerMeetingLogDTO>> getIncompleteSSMeetingLog(ZonedDateTime zonedDateTime);

    /**
     * Get the "id" signalServerMeetingLog.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<SignalServerMeetingLogDTO> findOne(Long id);

    /**
     * Delete the "id" signalServerMeetingLog.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
