package com.pmp.nwms.web.rest;
import com.pmp.nwms.service.SignalServerLogService;
import com.pmp.nwms.web.rest.errors.BadRequestAlertException;
import com.pmp.nwms.web.rest.util.HeaderUtil;
import com.pmp.nwms.web.rest.util.PaginationUtil;
import com.pmp.nwms.service.dto.SignalServerLogDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing SignalServerLog.
 */
@RestController
@RequestMapping("/api")
public class SignalServerLogResource {

    private final Logger log = LoggerFactory.getLogger(SignalServerLogResource.class);

    private static final String ENTITY_NAME = "signalServerLog";

    private final SignalServerLogService signalServerLogService;

    public SignalServerLogResource(SignalServerLogService signalServerLogService) {
        this.signalServerLogService = signalServerLogService;
    }

    /**
     * POST  /signal-server-logs : Create a new signalServerLog.
     *
     * @param signalServerLogDTO the signalServerLogDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new signalServerLogDTO, or with status 400 (Bad Request) if the signalServerLog has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/signal-server-logs")
    public ResponseEntity<SignalServerLogDTO> createSignalServerLog(@Valid @RequestBody SignalServerLogDTO signalServerLogDTO) throws URISyntaxException {
        log.debug("REST request to save SignalServerLog : {}", signalServerLogDTO);
        if (signalServerLogDTO.getId() != null) {
            throw new BadRequestAlertException("A new signalServerLog cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SignalServerLogDTO result = signalServerLogService.save(signalServerLogDTO);
        return ResponseEntity.created(new URI("/api/signal-server-logs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /signal-server-logs : Updates an existing signalServerLog.
     *
     * @param signalServerLogDTO the signalServerLogDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated signalServerLogDTO,
     * or with status 400 (Bad Request) if the signalServerLogDTO is not valid,
     * or with status 500 (Internal Server Error) if the signalServerLogDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/signal-server-logs")
    public ResponseEntity<SignalServerLogDTO> updateSignalServerLog(@Valid @RequestBody SignalServerLogDTO signalServerLogDTO) throws URISyntaxException {
        log.debug("REST request to update SignalServerLog : {}", signalServerLogDTO);
        if (signalServerLogDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SignalServerLogDTO result = signalServerLogService.save(signalServerLogDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, signalServerLogDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /signal-server-logs : get all the signalServerLogs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of signalServerLogs in body
     */
    @GetMapping("/signal-server-logs")
    public ResponseEntity<List<SignalServerLogDTO>> getAllSignalServerLogs(Pageable pageable) {
        log.debug("REST request to get a page of SignalServerLogs");
        Page<SignalServerLogDTO> page = signalServerLogService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/signal-server-logs");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /signal-server-logs/:id : get the "id" signalServerLog.
     *
     * @param id the id of the signalServerLogDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the signalServerLogDTO, or with status 404 (Not Found)
     */
    @GetMapping("/signal-server-logs/{id}")
    public ResponseEntity<SignalServerLogDTO> getSignalServerLog(@PathVariable Long id) {
        log.debug("REST request to get SignalServerLog : {}", id);
        Optional<SignalServerLogDTO> signalServerLogDTO = signalServerLogService.findOne(id);
        return ResponseUtil.wrapOrNotFound(signalServerLogDTO);
    }

    /**
     * DELETE  /signal-server-logs/:id : delete the "id" signalServerLog.
     *
     * @param id the id of the signalServerLogDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/signal-server-logs/{id}")
    public ResponseEntity<Void> deleteSignalServerLog(@PathVariable Long id) {
        log.debug("REST request to delete SignalServerLog : {}", id);
        signalServerLogService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
