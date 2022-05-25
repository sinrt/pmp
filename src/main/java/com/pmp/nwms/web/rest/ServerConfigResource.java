package com.pmp.nwms.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pmp.nwms.domain.ServerConfig;
import com.pmp.nwms.service.ServerConfigService;
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
 * REST controller for managing ServerConfig.
 */
@RestController
@RequestMapping("/api")
public class ServerConfigResource {

    private final Logger log = LoggerFactory.getLogger(ServerConfigResource.class);

    private static final String ENTITY_NAME = "serverConfig";

    private final ServerConfigService serverConfigService;

    public ServerConfigResource(ServerConfigService serverConfigService) {
        this.serverConfigService = serverConfigService;
    }

    /**
     * POST  /server-configs : Create a new serverConfig.
     *
     * @param serverConfig the serverConfig to create
     * @return the ResponseEntity with status 201 (Created) and with body the new serverConfig, or with status 400 (Bad Request) if the serverConfig has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/server-configs")
    @Timed
    public ResponseEntity<ServerConfig> createServerConfig(@RequestBody ServerConfig serverConfig) throws URISyntaxException {
        log.debug("REST request to updateClassroom ServerConfig : {}", serverConfig);
        if (serverConfig.getId() != null) {
            throw new BadRequestAlertException("A new serverConfig cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ServerConfig result = serverConfigService.save(serverConfig);
        return ResponseEntity.created(new URI("/api/server-configs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /server-configs : Updates an existing serverConfig.
     *
     * @param serverConfig the serverConfig to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated serverConfig,
     * or with status 400 (Bad Request) if the serverConfig is not valid,
     * or with status 500 (Internal Server Error) if the serverConfig couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/server-configs")
    @Timed
    public ResponseEntity<ServerConfig> updateServerConfig(@RequestBody ServerConfig serverConfig) throws URISyntaxException {
        log.debug("REST request to update ServerConfig : {}", serverConfig);
        if (serverConfig.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ServerConfig result = serverConfigService.save(serverConfig);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, serverConfig.getId().toString()))
            .body(result);
    }

    /**
     * GET  /server-configs : get all the serverConfigs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of serverConfigs in body
     */
    @GetMapping("/server-configs")
    @Timed
    public ResponseEntity<List<ServerConfig>> getAllServerConfigs(Pageable pageable) {
        log.debug("REST request to get a page of ServerConfigs");
        Page<ServerConfig> page = serverConfigService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/server-configs");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /server-configs/:id : get the "id" serverConfig.
     *
     * @param id the id of the serverConfig to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the serverConfig, or with status 404 (Not Found)
     */
    @GetMapping("/server-configs/{id}")
    @Timed
    public ResponseEntity<ServerConfig> getServerConfig(@PathVariable Long id) {
        log.debug("REST request to get ServerConfig : {}", id);
        Optional<ServerConfig> serverConfig = serverConfigService.findOne(id);
        return ResponseUtil.wrapOrNotFound(serverConfig);
    }

    @GetMapping("/server-configs/byParamName/{paramName}")
    @Timed
    public ResponseEntity<ServerConfig> getServerConfigByParamName(@PathVariable String paramName) {
        log.debug("REST request to get ServerConfig : {}", paramName);
        Optional<ServerConfig> serverConfig = serverConfigService.findOneByParamName(paramName);
        return ResponseUtil.wrapOrNotFound(serverConfig);
    }

    /**
     * DELETE  /server-configs/:id : delete the "id" serverConfig.
     *
     * @param id the id of the serverConfig to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/server-configs/{id}")
    @Timed
    public ResponseEntity<Void> deleteServerConfig(@PathVariable Long id) {
        log.debug("REST request to delete ServerConfig : {}", id);
        serverConfigService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
