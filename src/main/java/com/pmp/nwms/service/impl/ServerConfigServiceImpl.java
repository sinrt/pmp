package com.pmp.nwms.service.impl;

import com.pmp.nwms.service.ServerConfigService;
import com.pmp.nwms.domain.ServerConfig;
import com.pmp.nwms.repository.ServerConfigRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing ServerConfig.
 */
@Service("serverConfigService")
@Transactional
public class ServerConfigServiceImpl implements ServerConfigService {

    private final Logger log = LoggerFactory.getLogger(ServerConfigServiceImpl.class);

    private final ServerConfigRepository serverConfigRepository;

    public ServerConfigServiceImpl(ServerConfigRepository serverConfigRepository) {
        this.serverConfigRepository = serverConfigRepository;
    }

    @Override
    public Optional<ServerConfig> findOneByParamName(String paramName) {
        log.debug("Request to get ServerConfig : {}", paramName);
        return serverConfigRepository.findByParamName(paramName);
    }

    /**
     * Save a serverConfig.
     *
     * @param serverConfig the entity to updateClassroom
     * @return the persisted entity
     */
    @Override
    public ServerConfig save(ServerConfig serverConfig) {
        log.debug("Request to updateClassroom ServerConfig : {}", serverConfig);
        return serverConfigRepository.save(serverConfig);
    }

    public Optional<ServerConfig> findByParamName_(String paramName){
        return serverConfigRepository.findByParamName(paramName);
    }

    /**
     * Get all the serverConfigs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ServerConfig> findAll(Pageable pageable) {
        log.debug("Request to get all ServerConfigs");
        return serverConfigRepository.findAll(pageable);
    }


    /**
     * Get one serverConfig by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ServerConfig> findOne(Long id) {
        log.debug("Request to get ServerConfig : {}", id);
        return serverConfigRepository.findById(id);
    }

    /**
     * Delete the serverConfig by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ServerConfig : {}", id);
        serverConfigRepository.deleteById(id);
    }
}
