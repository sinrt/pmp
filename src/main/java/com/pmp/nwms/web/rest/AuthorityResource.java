package com.pmp.nwms.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pmp.nwms.domain.Authority;
import com.pmp.nwms.repository.AuthorityRepository;
import com.pmp.nwms.security.AuthoritiesConstants;
import com.pmp.nwms.web.rest.errors.BadRequestAlertException;
import com.pmp.nwms.web.rest.util.HeaderUtil;
import com.pmp.nwms.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.net.URI;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class AuthorityResource {

    private final AuthorityRepository authorityRepository;
    private final Logger log = LoggerFactory.getLogger(AuthorityResource.class);
    private static final String ENTITY_NAME = "authority";

    public AuthorityResource(AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }

    @PostMapping("/authorities")
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<Authority> createUser(@Valid @RequestBody Authority authority) throws URISyntaxException {
        if (authority.getName() == null)
            throw new BadRequestAlertException("A new authority cannot already have an ID", ENTITY_NAME, "idexists");
        authority = authorityRepository.save(authority);
        return ResponseEntity.created(new URI("/api/authorities/" + authority.getName()))
            .headers(HeaderUtil.createAlert("userManagement.created", authority.getName()))
            .body(authority);
    }

    @PutMapping("/authorities/{oldName}")
    @Timed
    public ResponseEntity<Authority> updateClassroom(@Valid @RequestBody Authority authority, @PathVariable String oldName) throws URISyntaxException {
        log.debug("REST request to update Classroom : {}", authority);
        if (oldName == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Authority deleteAthurity = authorityRepository.authorityByName(oldName);
        authorityRepository.delete(deleteAthurity);
        Authority result = authorityRepository.save(authority);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, authority.getName().toString()))
            .body(result);
    }

    @GetMapping("/authorities")
    @Timed
    public ResponseEntity<List<Authority>> getAllUsers(Pageable pageable) {
        final Page<Authority> page = authorityRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/authorities");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/authorities/{name}")
    @Timed
    public ResponseEntity<Authority> getClassroom(@PathVariable String name) {
        log.debug("REST request to get Classroom : {}", name);
        Optional<Authority> classroom = authorityRepository.findOneAuthorityByName(name);
        return ResponseUtil.wrapOrNotFound(classroom);
    }

    @DeleteMapping("/authorities/{name}")
    @Timed
    public ResponseEntity<Void> deleteClassroom(@PathVariable String name) {
        log.debug("REST request to delete Classroom : {}", name);
        Authority deleteAthurity = authorityRepository.authorityByName(name);
        authorityRepository.delete(deleteAthurity);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, name.toString())).build();
    }
}
