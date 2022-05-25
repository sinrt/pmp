package com.pmp.nwms.service.impl;

import com.pmp.nwms.service.DiscountsDefService;
import com.pmp.nwms.domain.DiscountsDef;
import com.pmp.nwms.repository.DiscountsDefRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing DiscountsDef.
 */
@Service
@Transactional
public class DiscountsDefServiceImpl implements DiscountsDefService {

    private final Logger log = LoggerFactory.getLogger(DiscountsDefServiceImpl.class);

    private final DiscountsDefRepository discountsDefRepository;

    public DiscountsDefServiceImpl(DiscountsDefRepository discountsDefRepository) {
        this.discountsDefRepository = discountsDefRepository;
    }

    /**
     * Save a discountsDef.
     *
     * @param discountsDef the entity to save
     * @return the persisted entity
     */
    @Override
    public DiscountsDef save(DiscountsDef discountsDef) {
        log.debug("Request to save DiscountsDef : {}", discountsDef);
        return discountsDefRepository.save(discountsDef);
    }

    /**
     * Get all the discountsDefs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DiscountsDef> findAll(Pageable pageable) {
        log.debug("Request to get all DiscountsDefs");
        return discountsDefRepository.findAll(pageable);
    }


    /**
     * Get one discountsDef by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<DiscountsDef> findOne(Long id) {
        log.debug("Request to get DiscountsDef : {}", id);
        return discountsDefRepository.findById(id);
    }

    @Override
    public Optional<DiscountsDef> findOneByCode(String code) {
        return discountsDefRepository.findOneByCaseSenseCode(code);
    }

    /**
     * Delete the discountsDef by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete DiscountsDef : {}", id);
        discountsDefRepository.deleteById(id);
    }
}
