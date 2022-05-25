package com.pmp.nwms.web.rest;

import com.pmp.nwms.service.SignalServerMeetingLogService;
import com.pmp.nwms.web.rest.errors.BadRequestAlertException;
import com.pmp.nwms.web.rest.util.HeaderUtil;
import com.pmp.nwms.web.rest.util.PaginationUtil;
import com.pmp.nwms.service.dto.SignalServerMeetingLogDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing SignalServerMeetingLog.
 */
@RestController
@RequestMapping("/api")
public class SignalServerMeetingLogResource {

    private final Logger log = LoggerFactory.getLogger(SignalServerMeetingLogResource.class);

    private static final String ENTITY_NAME = "signalServerMeetingLog";

    private final SignalServerMeetingLogService signalServerMeetingLogService;

    public SignalServerMeetingLogResource(SignalServerMeetingLogService signalServerMeetingLogService) {
        this.signalServerMeetingLogService = signalServerMeetingLogService;
    }

    /**
     * POST  /signal-server-meeting-logs : Create a new signalServerMeetingLog.
     *
     * @param signalServerMeetingLogDTO the signalServerMeetingLogDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new signalServerMeetingLogDTO, or with status 400 (Bad Request) if the signalServerMeetingLog has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/signal-server-meeting-logs")
    public ResponseEntity<SignalServerMeetingLogDTO> createSignalServerMeetingLog(@RequestBody SignalServerMeetingLogDTO signalServerMeetingLogDTO) throws URISyntaxException {
        log.debug("REST request to save SignalServerMeetingLog : {}", signalServerMeetingLogDTO);
        if (signalServerMeetingLogDTO.getId() != null) {
            throw new BadRequestAlertException("A new signalServerMeetingLog cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SignalServerMeetingLogDTO result = signalServerMeetingLogService.save(signalServerMeetingLogDTO);
        return ResponseEntity.created(new URI("/api/signal-server-meeting-logs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /signal-server-meeting-logs : Updates an existing signalServerMeetingLog.
     *
     * @param signalServerMeetingLogDTO the signalServerMeetingLogDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated signalServerMeetingLogDTO,
     * or with status 400 (Bad Request) if the signalServerMeetingLogDTO is not valid,
     * or with status 500 (Internal Server Error) if the signalServerMeetingLogDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/signal-server-meeting-logs")
    public ResponseEntity<SignalServerMeetingLogDTO> updateSignalServerMeetingLog(@RequestBody SignalServerMeetingLogDTO signalServerMeetingLogDTO) throws URISyntaxException {
        log.debug("REST request to update SignalServerMeetingLog : {}", signalServerMeetingLogDTO);
        if (signalServerMeetingLogDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SignalServerMeetingLogDTO result = signalServerMeetingLogService.save(signalServerMeetingLogDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, signalServerMeetingLogDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /signal-server-meeting-logs : get all the signalServerMeetingLogs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of signalServerMeetingLogs in body
     */
    @GetMapping("/signal-server-meeting-logs")
    public ResponseEntity<List<SignalServerMeetingLogDTO>> getAllSignalServerMeetingLogs(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean isupdate) {
        log.debug("REST request to get a page of SignalServerMeetingLogs");
        Page<SignalServerMeetingLogDTO> page = signalServerMeetingLogService.findAll(pageable,isupdate);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/signal-server-meeting-logs");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /signal-server-meeting-logs/:id : get the "id" signalServerMeetingLog.
     *
     * @param id the id of the signalServerMeetingLogDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the signalServerMeetingLogDTO, or with status 404 (Not Found)
     */
    @GetMapping("/signal-server-meeting-logs/{id}")
    public ResponseEntity<SignalServerMeetingLogDTO> getSignalServerMeetingLog(@PathVariable Long id) {
        log.debug("REST request to get SignalServerMeetingLog : {}", id);
        Optional<SignalServerMeetingLogDTO> signalServerMeetingLogDTO = signalServerMeetingLogService.findOne(id);
        return ResponseUtil.wrapOrNotFound(signalServerMeetingLogDTO);
    }

    /**
     * DELETE  /signal-server-meeting-logs/:id : delete the "id" signalServerMeetingLog.
     *
     * @param id the id of the signalServerMeetingLogDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/signal-server-meeting-logs/{id}")
    public ResponseEntity<Void> deleteSignalServerMeetingLog(@PathVariable Long id) {
        log.debug("REST request to delete SignalServerMeetingLog : {}", id);
        signalServerMeetingLogService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
