package com.pmp.nwms.repository;

import com.pmp.nwms.domain.PersistentAuditEvent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.Date;
import java.util.List;

/**
 * Spring Data JPA repository for the PersistentAuditEvent entity.
 */
public interface PersistenceAuditEventRepository extends JpaRepository<PersistentAuditEvent, Long> {

    List<PersistentAuditEvent> findByPrincipal(String principal);

    List<PersistentAuditEvent> findByAuditEventDateAfter(Instant after);

    List<PersistentAuditEvent> findByPrincipalAndAuditEventDateAfter(String principal, Instant after);

    List<PersistentAuditEvent> findByPrincipalAndAuditEventDateAfterAndAuditEventType(String principal, Instant after, String type);

    Page<PersistentAuditEvent> findAllByAuditEventDateBetween(Instant fromDate, Instant toDate, Pageable pageable);

    @Query("SELECT u FROM PersistentAuditEvent u WHERE u.principal = ?1 AND u.auditEventType = ?2 ")
    Page<PersistentAuditEvent> findAllByAuditEventPrincipal(String principal, String authType, Instant fromDate, Instant toDate, Pageable pageable);

    @Query("select o from PersistentAuditEvent o where o.principal = :principal and o.auditEventDate > :checkDate order by o.auditEventDate desc")
    List<PersistentAuditEvent> getByPrincipalAndAfterAuditEventDate(@Param("principal") String principal, @Param("checkDate") Instant checkDate, Pageable pageable);

}
