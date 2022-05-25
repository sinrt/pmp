package com.pmp.nwms.repository;

import com.pmp.nwms.domain.ArchivedRubruSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArchivedRubruSessionRepository extends JpaRepository<ArchivedRubruSession, Long> {
}
