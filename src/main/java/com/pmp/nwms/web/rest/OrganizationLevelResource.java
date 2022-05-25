package com.pmp.nwms.web.rest;
import com.pmp.nwms.domain.OrganizationLevel;
import com.pmp.nwms.service.OrganizationLevelService;
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

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing OrganizationLevel.
 */
@RestController
@RequestMapping("/api")
public class OrganizationLevelResource {

    private final Logger log = LoggerFactory.getLogger(OrganizationLevelResource.class);

    private static final String ENTITY_NAME = "organizationLevel";

    private final OrganizationLevelService organizationLevelService;

    public OrganizationLevelResource(OrganizationLevelService organizationLevelService) {
        this.organizationLevelService = organizationLevelService;
    }

    /**
     * POST  /organization-levels : Create a new organizationLevel.
     *
     * @param organizationLevel the organizationLevel to create
     * @return the ResponseEntity with status 201 (Created) and with body the new organizationLevel, or with status 400 (Bad Request) if the organizationLevel has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/organization-levels")
    public ResponseEntity<OrganizationLevel> createOrganizationLevel(@Valid @RequestBody OrganizationLevel organizationLevel) throws URISyntaxException {
        log.debug("REST request to updateClassroom OrganizationLevel : {}", organizationLevel);
        if (organizationLevel.getId() != null) {
            throw new BadRequestAlertException("A new organizationLevel cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrganizationLevel result = organizationLevelService.save(organizationLevel);
        return ResponseEntity.created(new URI("/api/organization-levels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /organization-levels : Updates an existing organizationLevel.
     *
     * @param organizationLevel the organizationLevel to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated organizationLevel,
     * or with status 400 (Bad Request) if the organizationLevel is not valid,
     * or with status 500 (Internal Server Error) if the organizationLevel couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/organization-levels")
    public ResponseEntity<OrganizationLevel> updateOrganizationLevel(@Valid @RequestBody OrganizationLevel organizationLevel) throws URISyntaxException {
        log.debug("REST request to update OrganizationLevel : {}", organizationLevel);
        if (organizationLevel.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OrganizationLevel result = organizationLevelService.save(organizationLevel);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, organizationLevel.getId().toString()))
            .body(result);
    }

    /**
     * GET  /organization-levels : get all the organizationLevels.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of organizationLevels in body
     */
    @GetMapping("/organization-levels")
    public ResponseEntity<List<OrganizationLevel>> getAllOrganizationLevels(Pageable pageable) {
        log.debug("REST request to get a page of OrganizationLevels");
        Page<OrganizationLevel> page = organizationLevelService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/organization-levels");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /organization-levels/:id : get the "id" organizationLevel.
     *
     * @param id the id of the organizationLevel to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the organizationLevel, or with status 404 (Not Found)
     */
    @GetMapping("/organization-levels/{id}")
    public ResponseEntity<OrganizationLevel> getOrganizationLevel(@PathVariable Long id) {
        log.debug("REST request to get OrganizationLevel : {}", id);
        Optional<OrganizationLevel> organizationLevel = organizationLevelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(organizationLevel);
    }

    /**
     * DELETE  /organization-levels/:id : delete the "id" organizationLevel.
     *
     * @param id the id of the organizationLevel to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/organization-levels/{id}")
    public ResponseEntity<Void> deleteOrganizationLevel(@PathVariable Long id) {
        log.debug("REST request to delete OrganizationLevel : {}", id);
        organizationLevelService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
