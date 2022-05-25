package com.pmp.nwms.service;

import com.pmp.nwms.domain.ServerConfig;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing ServerConfig.
 */
public interface ServerConfigService {

    /**
     * Save a serverConfig.
     *
     * @param serverConfig the entity to updateClassroom
     * @return the persisted entity
     */
    ServerConfig save(ServerConfig serverConfig);

    Optional<ServerConfig> findOneByParamName(String paramName);
    /**
     * Get all the serverConfigs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ServerConfig> findAll(Pageable pageable);


    /**
     * Get the "id" serverConfig.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ServerConfig> findOne(Long id);

    /**
     * Delete the "id" serverConfig.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    Optional<ServerConfig> findByParamName_(String paramName);
}
