package com.pmp.nwms.repository;

import com.pmp.nwms.domain.DiscountsDef;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Spring Data  repository for the DiscountsDef entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DiscountsDefRepository extends JpaRepository<DiscountsDef, Long> {
    Optional<DiscountsDef> findOneByCode(String code);
    @Query("select discountsdef from DiscountsDef discountsdef where discountsdef.code like :code")
    public Optional<DiscountsDef> findOneByCaseSenseCode(@Param("code") String code);

}
