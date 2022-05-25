package com.pmp.nwms.service;

import com.pmp.nwms.domain.OrganizationLevel;
import com.pmp.nwms.repository.OrganizationLevelRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing OrganizationLevel.
 */
@Service
@Transactional
public class OrganizationLevelService {

    private final Logger log = LoggerFactory.getLogger(OrganizationLevelService.class);

    private final OrganizationLevelRepository organizationLevelRepository;

    public OrganizationLevelService(OrganizationLevelRepository organizationLevelRepository) {
        this.organizationLevelRepository = organizationLevelRepository;
    }

    /**
     * Save a organizationLevel.
     *
     * @param organizationLevel the entity to updateClassroom
     * @return the persisted entity
     */
    public OrganizationLevel save(OrganizationLevel organizationLevel) {
        log.debug("Request to updateClassroom OrganizationLevel : {}", organizationLevel);
        return organizationLevelRepository.save(organizationLevel);
    }

    public OrganizationLevel findOrganizationLevelByName(String name) {
        log.debug("Request to find OrganizationLevel By Name: {}", name);
        return organizationLevelRepository.findOrganizationLevelByName(name);
    }

    /**
     * Get all the organizationLevels.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<OrganizationLevel> findAll(Pageable pageable) {
        log.debug("Request to get all OrganizationLevels");
        return organizationLevelRepository.findAll(pageable);
    }


    /**
     * Get one organizationLevel by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<OrganizationLevel> findOne(Long id) {
        log.debug("Request to get OrganizationLevel : {}", id);
        return organizationLevelRepository.findById(id);
    }

    /**
     * Delete the organizationLevel by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete OrganizationLevel : {}", id);
        organizationLevelRepository.deleteById(id);
    }
}
