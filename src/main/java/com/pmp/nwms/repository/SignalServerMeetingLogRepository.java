package com.pmp.nwms.repository;

import com.pmp.nwms.domain.SignalServerMeetingLog;
import org.joda.time.DateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the SignalServerMeetingLog entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SignalServerMeetingLogRepository extends JpaRepository<SignalServerMeetingLog, Long> {

    @Query(value = "select signalservermeetinglog from SignalServerMeetingLog signalservermeetinglog where " +
        " signalservermeetinglog.sessionStartDate > :day")
    Optional<List<SignalServerMeetingLog>> getIncompleteSSMeetingLog(@Param("day") ZonedDateTime day);
}
