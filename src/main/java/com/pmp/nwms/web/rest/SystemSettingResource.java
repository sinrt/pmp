package com.pmp.nwms.web.rest;
import com.pmp.nwms.domain.SystemSetting;
import com.pmp.nwms.service.SystemSettingService;
import com.pmp.nwms.web.rest.errors.BadRequestAlertException;
import com.pmp.nwms.web.rest.util.HeaderUtil;
import com.pmp.nwms.web.rest.util.PaginationUtil;
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
 * REST controller for managing SystemSetting.
 */
@RestController
@RequestMapping("/api")
public class SystemSettingResource {

    private final Logger log = LoggerFactory.getLogger(SystemSettingResource.class);

    private static final String ENTITY_NAME = "systemSetting";

    private final SystemSettingService systemSettingService;

    public SystemSettingResource(SystemSettingService systemSettingService) {
        this.systemSettingService = systemSettingService;
    }

    /**
     * POST  /system-settings : Create a new systemSetting.
     *
     * @param systemSetting the systemSetting to create
     * @return the ResponseEntity with status 201 (Created) and with body the new systemSetting, or with status 400 (Bad Request) if the systemSetting has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/system-settings")
    public ResponseEntity<SystemSetting> createSystemSetting(@RequestBody SystemSetting systemSetting) throws URISyntaxException {
        log.debug("REST request to updateClassroom SystemSetting : {}", systemSetting);
        if (systemSetting.getId() != null) {
            throw new BadRequestAlertException("A new systemSetting cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SystemSetting result = systemSettingService.save(systemSetting);
        return ResponseEntity.created(new URI("/api/system-settings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /system-settings : Updates an existing systemSetting.
     *
     * @param systemSetting the systemSetting to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated systemSetting,
     * or with status 400 (Bad Request) if the systemSetting is not valid,
     * or with status 500 (Internal Server Error) if the systemSetting couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/system-settings")
    public ResponseEntity<SystemSetting> updateSystemSetting(@RequestBody SystemSetting systemSetting) throws URISyntaxException {
        log.debug("REST request to update SystemSetting : {}", systemSetting);
        if (systemSetting.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SystemSetting result = systemSettingService.save(systemSetting);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, systemSetting.getId().toString()))
            .body(result);
    }

    /**
     * GET  /system-settings : get all the systemSettings.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of systemSettings in body
     */
    @GetMapping("/system-settings")
    public ResponseEntity<List<SystemSetting>> getAllSystemSettings(Pageable pageable) {
        log.debug("REST request to get a page of SystemSettings");
        Page<SystemSetting> page = systemSettingService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/system-settings");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /system-settings/:id : get the "id" systemSetting.
     *
     * @param id the id of the systemSetting to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the systemSetting, or with status 404 (Not Found)
     */
    @GetMapping("/system-settings/{id}")
    public ResponseEntity<SystemSetting> getSystemSetting(@PathVariable Long id) {
        log.debug("REST request to get SystemSetting : {}", id);
        Optional<SystemSetting> systemSetting = systemSettingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(systemSetting);
    }

    /**
     * DELETE  /system-settings/:id : delete the "id" systemSetting.
     *
     * @param id the id of the systemSetting to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/system-settings/{id}")
    public ResponseEntity<Void> deleteSystemSetting(@PathVariable Long id) {
        log.debug("REST request to delete SystemSetting : {}", id);
        systemSettingService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
