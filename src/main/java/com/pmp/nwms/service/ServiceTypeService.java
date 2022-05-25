package com.pmp.nwms.service;

import com.pmp.nwms.service.dto.ServiceTypeDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing ServiceType.
 */
public interface ServiceTypeService {

    /**
     * Save a serviceType.
     *
     * @param serviceTypeDTO the entity to updateClassroom
     * @return the persisted entity
     */
    ServiceTypeDTO save(ServiceTypeDTO serviceTypeDTO);

    /**
     * Get all the serviceTypes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ServiceTypeDTO> findAll(Pageable pageable);


    /**
     * Get the "id" serviceType.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ServiceTypeDTO> findOne(Long id);

    /**
     * Delete the "id" serviceType.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
