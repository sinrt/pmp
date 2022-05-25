package com.pmp.nwms.service;

import com.pmp.nwms.service.dto.PurchaseLogDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing PurchaseLog.
 */
public interface PurchaseLogService {

    /**
     * Save a purchaseLog.
     *
     * @param purchaseLogDTO the entity to save
     * @return the persisted entity
     */
    PurchaseLogDTO save(PurchaseLogDTO purchaseLogDTO);

    /**
     * Get all the purchaseLogs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<PurchaseLogDTO> findAll(Pageable pageable);


    /**
     * Get the "id" purchaseLog.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<PurchaseLogDTO> findOne(Long id);

    /**
     * Delete the "id" purchaseLog.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
