package com.pmp.nwms.service.impl;

import com.pmp.nwms.service.PaymentPeriodService;
import com.pmp.nwms.domain.PaymentPeriod;
import com.pmp.nwms.repository.PaymentPeriodRepository;
import com.pmp.nwms.service.dto.PaymentPeriodDTO;
import com.pmp.nwms.service.mapper.PaymentPeriodMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing PaymentPeriod.
 */
@Service
@Transactional
public class PaymentPeriodServiceImpl implements PaymentPeriodService {

    private final Logger log = LoggerFactory.getLogger(PaymentPeriodServiceImpl.class);

    private final PaymentPeriodRepository paymentPeriodRepository;

    private final PaymentPeriodMapper paymentPeriodMapper;

    public PaymentPeriodServiceImpl(PaymentPeriodRepository paymentPeriodRepository, PaymentPeriodMapper paymentPeriodMapper) {
        this.paymentPeriodRepository = paymentPeriodRepository;
        this.paymentPeriodMapper = paymentPeriodMapper;
    }

    /**
     * Save a paymentPeriod.
     *
     * @param paymentPeriodDTO the entity to updateClassroom
     * @return the persisted entity
     */
    @Override
    public PaymentPeriodDTO save(PaymentPeriodDTO paymentPeriodDTO) {
        log.debug("Request to updateClassroom PaymentPeriod : {}", paymentPeriodDTO);
        PaymentPeriod paymentPeriod = paymentPeriodMapper.toEntity(paymentPeriodDTO);
        paymentPeriod = paymentPeriodRepository.save(paymentPeriod);
        return paymentPeriodMapper.toDto(paymentPeriod);
    }

    /**
     * Get all the paymentPeriods.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PaymentPeriodDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PaymentPeriods");
        return paymentPeriodRepository.findAll(pageable)
            .map(paymentPeriodMapper::toDto);
    }

    @Override
    public Page<PaymentPeriodDTO> getActivePaymentPeriods(Pageable pageable) {
        return paymentPeriodRepository.findAllByActive(pageable,true).map(PaymentPeriodDTO::new);
    }


    /**
     * Get one paymentPeriod by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PaymentPeriodDTO> findOne(Long id) {
        log.debug("Request to get PaymentPeriod : {}", id);
        return paymentPeriodRepository.findById(id)
            .map(paymentPeriodMapper::toDto);
    }

    /**
     * Delete the paymentPeriod by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PaymentPeriod : {}", id);        paymentPeriodRepository.deleteById(id);
    }
}
