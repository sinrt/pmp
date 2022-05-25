package com.pmp.nwms.service;

import com.pmp.nwms.domain.SuggestionsBox;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing SuggestionsBox.
 */
public interface SuggestionsBoxService {

    /**
     * Save a suggestionsBox.
     *
     * @param suggestionsBox the entity to save
     * @return the persisted entity
     */
    SuggestionsBox save(SuggestionsBox suggestionsBox);

    /**
     * Get all the suggestionsBoxes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<SuggestionsBox> findAll(Pageable pageable);


    /**
     * Get the "id" suggestionsBox.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<SuggestionsBox> findOne(Long id);

    /**
     * Delete the "id" suggestionsBox.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
