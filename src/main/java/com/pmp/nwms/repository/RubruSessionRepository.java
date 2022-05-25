package com.pmp.nwms.repository;

import com.pmp.nwms.domain.RubruSession;
import com.pmp.nwms.service.listmodel.RubruSessionListModel;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface RubruSessionRepository extends JpaRepository<RubruSession, Long> {
    @Query("select o from RubruSession o where o.sessionId = :sessionId and o.startDate <= :date and o.ovwsUrl = :ovwsUrl and o.appUrl = :appUrl and (o.endDate is null or o.endDate >= :date)")
    Optional<RubruSession> findActiveSession(@Param("sessionId") String sessionId, @Param("date") Date date, @Param("ovwsUrl") String ovwsUrl, @Param("appUrl") String appUrl);

    @Query("select count(o) from RubruSession o where o.sessionId = :sessionId and o.endDate is null")
    long countOpenSessions(@Param("sessionId") String sessionId);

    @Modifying
    @Query("update RubruSession o set o.endDate = :closeDate, o.endReason = :closeReason where o.id in :ids and o.endDate is null")
    void closeAllOpenSessions(@Param("ids") List<Long> ids, @Param("closeDate") Date closeDate, @Param("closeReason") String closeReason);

    @Query("select o.id from RubruSession o where o.sessionId = :sessionId and o.ovwsUrl = :ovwsUrl and o.appUrl = :appUrl and o.endDate is null")
    List<Long> getOpenSessionIds(@Param("sessionId") String sessionId, @Param("ovwsUrl") String ovwsUrl, @Param("appUrl") String appUrl);

    @Query("select o from RubruSession o where o.classroomId = :classroomId")
    List<RubruSession> getByClassroomId(@Param("classroomId") Long classroomId);

    @Query("select " +
        " o.id as id, " +
        " o.sessionId as sessionId, " +
        " o.sessionName as sessionName, " +
        " o.startDate as startDate, " +
        " o.endDate as endDate, " +
        " o.duration as duration, " +
        " o.classroomId as classroomId " +
        " from RubruSession o where o.classroomId = :classroomId")
    List<RubruSessionListModel> getRubruSessionListModelsByClassroomId(@Param("classroomId") Long classroomId);

    @Query("select sum(o.duration) from RubruSession o where o.creatorId = :classroomOwnerId and o.startDate >= :fromDate and o.startDate <= :toDate and o.endReason in :endReasons and o.endDate is not null")
    Long sumClassroomCreatorDurationInTimePeriod(@Param("classroomOwnerId") Long classroomOwnerId, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate, @Param("endReasons") List<String> endReasons);

    List<RubruSession> findAllByStartDateBefore(Date checkDate, @Param("pageable") Pageable pageable);
}
