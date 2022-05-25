package com.pmp.nwms.repository;

import com.pmp.nwms.domain.PaymentPeriod;
import com.pmp.nwms.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PaymentPeriod entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PaymentPeriodRepository extends JpaRepository<PaymentPeriod, Long> {
    Page<PaymentPeriod> findAllByActive(Pageable pageable, boolean activated);
}
