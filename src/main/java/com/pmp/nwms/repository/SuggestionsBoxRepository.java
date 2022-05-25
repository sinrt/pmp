package com.pmp.nwms.repository;

import com.pmp.nwms.domain.SuggestionsBox;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SuggestionsBox entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SuggestionsBoxRepository extends JpaRepository<SuggestionsBox, Long> {

}
