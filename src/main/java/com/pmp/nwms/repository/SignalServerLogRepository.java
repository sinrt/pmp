package com.pmp.nwms.repository;

import com.pmp.nwms.domain.SignalServerLog;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * Spring Data  repository for the SignalServerLog entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SignalServerLogRepository extends JpaRepository<SignalServerLog, Long> {

    @Query(value = "select signalserverlog from SignalServerLog signalserverlog where signalserverlog.description like '%sessionCreated%' and signalserverlog.check is false")
    Page<SignalServerLog> findAllNewSessions(Pageable pageable);

    @Modifying
    @Query("UPDATE SignalServerLog signalserverlog SET signalserverlog.check = true")
    void setAllCheckToTrue();

    @Query(value = "SELECT * FROM signal_server_log  where  (description like '%sessionDestroyed%' OR description like '%sessionCreated%')  and description like %:sessionName%  and id>:id order by id asc limit 2 ", nativeQuery = true)
    Optional<List<SignalServerLog>> findSessionDestroy(@Param("sessionName") String sessionName, @Param("id") Long id);

}
