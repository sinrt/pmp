package com.pmp.nwms.service.impl;

import com.pmp.nwms.service.ServiceTypeService;
import com.pmp.nwms.domain.ServiceType;
import com.pmp.nwms.repository.ServiceTypeRepository;
import com.pmp.nwms.service.dto.ServiceTypeDTO;
import com.pmp.nwms.service.mapper.ServiceTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing ServiceType.
 */
@Service
@Transactional
public class ServiceTypeServiceImpl implements ServiceTypeService {

    private final Logger log = LoggerFactory.getLogger(ServiceTypeServiceImpl.class);

    private final ServiceTypeRepository serviceTypeRepository;

    private final ServiceTypeMapper serviceTypeMapper;

    public ServiceTypeServiceImpl(ServiceTypeRepository serviceTypeRepository, ServiceTypeMapper serviceTypeMapper) {
        this.serviceTypeRepository = serviceTypeRepository;
        this.serviceTypeMapper = serviceTypeMapper;
    }

    /**
     * Save a serviceType.
     *
     * @param serviceTypeDTO the entity to updateClassroom
     * @return the persisted entity
     */
    @Override
    public ServiceTypeDTO save(ServiceTypeDTO serviceTypeDTO) {
        log.debug("Request to updateClassroom ServiceType : {}", serviceTypeDTO);
        ServiceType serviceType = serviceTypeMapper.toEntity(serviceTypeDTO);
        serviceType = serviceTypeRepository.save(serviceType);
        return serviceTypeMapper.toDto(serviceType);
    }

    /**
     * Get all the serviceTypes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ServiceTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ServiceTypes");
        return serviceTypeRepository.findAll(pageable)
            .map(serviceTypeMapper::toDto);
    }


    /**
     * Get one serviceType by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ServiceTypeDTO> findOne(Long id) {
        log.debug("Request to get ServiceType : {}", id);
        return serviceTypeRepository.findById(id)
            .map(serviceTypeMapper::toDto);
    }

    /**
     * Delete the serviceType by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ServiceType : {}", id);        serviceTypeRepository.deleteById(id);
    }
}
