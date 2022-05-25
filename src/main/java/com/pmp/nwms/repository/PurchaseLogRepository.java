package com.pmp.nwms.repository;

import com.pmp.nwms.domain.PurchaseLog;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PurchaseLog entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PurchaseLogRepository extends JpaRepository<PurchaseLog, Long> {

    long countByUserID(Long userId);
}
