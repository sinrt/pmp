package com.pmp.nwms.service;

import com.pmp.nwms.domain.DiscountsDef;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing DiscountsDef.
 */
public interface DiscountsDefService {

    /**
     * Save a discountsDef.
     *
     * @param discountsDef the entity to save
     * @return the persisted entity
     */
    DiscountsDef save(DiscountsDef discountsDef);

    /**
     * Get all the discountsDefs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<DiscountsDef> findAll(Pageable pageable);


    /**
     * Get the "id" discountsDef.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<DiscountsDef> findOne(Long id);

    Optional<DiscountsDef> findOneByCode(String id);

    /**
     * Delete the "id" discountsDef.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
