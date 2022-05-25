package com.pmp.nwms.service.impl;

import com.pmp.nwms.service.SuggestionsBoxService;
import com.pmp.nwms.domain.SuggestionsBox;
import com.pmp.nwms.repository.SuggestionsBoxRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing SuggestionsBox.
 */
@Service
@Transactional
public class SuggestionsBoxServiceImpl implements SuggestionsBoxService {

    private final Logger log = LoggerFactory.getLogger(SuggestionsBoxServiceImpl.class);

    private final SuggestionsBoxRepository suggestionsBoxRepository;

    public SuggestionsBoxServiceImpl(SuggestionsBoxRepository suggestionsBoxRepository) {
        this.suggestionsBoxRepository = suggestionsBoxRepository;
    }

    /**
     * Save a suggestionsBox.
     *
     * @param suggestionsBox the entity to save
     * @return the persisted entity
     */
    @Override
    public SuggestionsBox save(SuggestionsBox suggestionsBox) {
        log.debug("Request to save SuggestionsBox : {}", suggestionsBox);
        return suggestionsBoxRepository.save(suggestionsBox);
    }

    /**
     * Get all the suggestionsBoxes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SuggestionsBox> findAll(Pageable pageable) {
        log.debug("Request to get all SuggestionsBoxes");
        return suggestionsBoxRepository.findAll(pageable);
    }


    /**
     * Get one suggestionsBox by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SuggestionsBox> findOne(Long id) {
        log.debug("Request to get SuggestionsBox : {}", id);
        return suggestionsBoxRepository.findById(id);
    }

    /**
     * Delete the suggestionsBox by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SuggestionsBox : {}", id);
        suggestionsBoxRepository.deleteById(id);
    }
}
