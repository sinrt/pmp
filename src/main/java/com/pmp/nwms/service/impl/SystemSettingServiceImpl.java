package com.pmp.nwms.service.impl;

import com.pmp.nwms.service.SystemSettingService;
import com.pmp.nwms.domain.SystemSetting;
import com.pmp.nwms.repository.SystemSettingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing SystemSetting.
 */
@Service
@Transactional
public class SystemSettingServiceImpl implements SystemSettingService {

    private final Logger log = LoggerFactory.getLogger(SystemSettingServiceImpl.class);

    private final SystemSettingRepository systemSettingRepository;

    public SystemSettingServiceImpl(SystemSettingRepository systemSettingRepository) {
        this.systemSettingRepository = systemSettingRepository;
    }

    /**
     * Save a systemSetting.
     *
     * @param systemSetting the entity to updateClassroom
     * @return the persisted entity
     */
    @Override
    public SystemSetting save(SystemSetting systemSetting) {
        log.debug("Request to updateClassroom SystemSetting : {}", systemSetting);
        return systemSettingRepository.save(systemSetting);
    }

    /**
     * Get all the systemSettings.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SystemSetting> findAll(Pageable pageable) {
        log.debug("Request to get all SystemSettings");
        return systemSettingRepository.findAll(pageable);
    }


    /**
     * Get one systemSetting by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SystemSetting> findOne(Long id) {
        log.debug("Request to get SystemSetting : {}", id);
        return systemSettingRepository.findById(id);
    }

    /**
     * Delete the systemSetting by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SystemSetting : {}", id);
        systemSettingRepository.deleteById(id);
    }
}
