package com.pmp.nwms.service;

import com.pmp.nwms.service.dto.PaymentPeriodDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing PaymentPeriod.
 */
public interface PaymentPeriodService {

    /**
     * Save a paymentPeriod.
     *
     * @param paymentPeriodDTO the entity to updateClassroom
     * @return the persisted entity
     */
    PaymentPeriodDTO save(PaymentPeriodDTO paymentPeriodDTO);

    /**
     * Get all the paymentPeriods.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<PaymentPeriodDTO> findAll(Pageable pageable);

    Page<PaymentPeriodDTO> getActivePaymentPeriods(Pageable pageable);


    /**
     * Get the "id" paymentPeriod.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<PaymentPeriodDTO> findOne(Long id);

    /**
     * Delete the "id" paymentPeriod.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
