package com.pmp.nwms.repository;

import com.pmp.nwms.domain.RubruSessionParticipant;
import com.pmp.nwms.model.OpenParticipantModel;
import com.pmp.nwms.web.rest.vm.RubruSessionParticipantsReportVM;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface RubruSessionParticipantRepository extends JpaRepository<RubruSessionParticipant, Long> {
    @Query("select o from RubruSessionParticipant o where o.rubruSession.id = :sessionId and o.participantId = :participantId")
    Optional<RubruSessionParticipant> findBySessionAndParticipantId(@Param("sessionId") Long sessionId, @Param("participantId") String participantId);

    @Query("select count(o) from RubruSessionParticipant o where o.participantId = :participantId and o.rubruSession.id = :rubruSessionId and o.leaveDateTime is null")
    long countOpenParticipants(@Param("participantId") String participantId, @Param("rubruSessionId") Long rubruSessionId);

    @Modifying
    @Query("update RubruSessionParticipant o set o.leaveDateTime = :closeDate, o.leaveReason = :closeReason where o.participantId = :participantId and o.rubruSession.id = :rubruSessionId and o.leaveDateTime is null")
    void closeAllOpenParticipants(@Param("participantId") String participantId, @Param("rubruSessionId") Long rubruSessionId, @Param("closeDate") Date closeDate, @Param("closeReason") String closeReason);

    @Query("select count(o) from RubruSessionParticipant o inner join o.rubruSession os where os.classroomId = :classroomId and os.endDate is null and o.leaveDateTime is null")
    Long countCurrentByClassroomId(@Param("classroomId") Long classroomId);

    @Query("select count(o) from RubruSessionParticipant o inner join o.rubruSession os where os.creatorId = :classroomOwnerId and os.endDate is null and o.leaveDateTime is null")
    Long countCurrentByClassroomOwnerId(@Param("classroomOwnerId") Long classroomOwnerId);

    @Modifying
    @Query("update RubruSessionParticipant o set o.leaveDateTime = :closeDate, o.leaveReason = :closeReason where o.rubruSession.id in :rubruSessionIds and o.leaveDateTime is null")
    void closeAllOpenParticipantsForRubruSessionIds(@Param("rubruSessionIds") List<Long> rubruSessionIds, @Param("closeDate") Date closeDate, @Param("closeReason") String closeReason);

    @Query("select count(o) from RubruSessionParticipant o inner join o.rubruSession os where o.userToken = :userToken and o.leaveDateTime is null and os.endDate is null")
    long countByUserToken(@Param("userToken") String userToken);

    @Query("select o from RubruSessionParticipant o where o.participantId = :participantId")
    Optional<RubruSessionParticipant> findByParticipantId(@Param("participantId") String participantId);

    @Query("select " +
        " o.participantName as participantName, " +
        " o.duration as duration, " +
        " o.joinDateTime as joinDateTime, " +
        " o.leaveDateTime as leaveDateTime," +
        " u.login as login," +
        " o.participantKey as participantKey " +
        " from RubruSessionParticipant o left outer join User u on (o.userId = u.id) where o.rubruSession.id = :rubruSessionId order by o.joinDateTime asc")
    List<RubruSessionParticipantsReportVM> getRubruSessionParticipantsReportVMs(@Param("rubruSessionId") Long rubruSessionId);

    @Query("select o from RubruSessionParticipant o inner join o.rubruSession os where os.classroomId = :classroomId and o.clientId in :clientIds")
    List<RubruSessionParticipant> findByClassroomIdAndClientIds(@Param("classroomId") Long classroomId, @Param("clientIds") List<String> clientIds);

    @Query("select count(o) from RubruSessionParticipant o inner join o.rubruSession os where os.classroomId = :classroomId and os.endDate is null and o.leaveDateTime is null")
    long countActiveParticipants(@Param("classroomId") Long classroomId);

    @Query("select o from RubruSessionParticipant o where o.rubruSession.id = :rubruSessionId and o.leaveDateTime is null")
    List<RubruSessionParticipant> findOpenParticipantsUsingRubruSessionId(@Param("rubruSessionId") Long rubruSessionId);

    @Query("select " +
        " os.endDate as sessionEndDate, " +
        " o.id as rubruSessionParticipantId, " +
        " o.joinDateTime as participantJoinDateTime " +
        " from RubruSessionParticipant o inner join o.rubruSession os where os.endDate is not null and o.leaveDateTime is null")
    List<OpenParticipantModel> findOpenParticipantsWithClosedSessions();

    @Transactional
    @Modifying
    @Query("update RubruSessionParticipant o set o.duration = :duration, o.leaveDateTime = :leaveDateTime, o.leaveReason = :leaveReason where o.id = :id")
    void closeParticipant(@Param("id") Long id, @Param("duration") Long duration, @Param("leaveDateTime") Date leaveDateTime, @Param("leaveReason") String leaveReason);

    List<RubruSessionParticipant> getByRubruSessionId(Long rubruSessionId);

    @Query("select count(o) from RubruSessionParticipant o inner join o.rubruSession os where o.userToken = :userToken and o.clientId <> :clientId and o.leaveDateTime is null and os.endDate is null")
    long countByUserTokenAndNotClientId(@Param("userToken") String userToken, @Param("clientId") String clientId);

    @Query("select count(o) from RubruSessionParticipant o inner join o.rubruSession os where os.classroomId = :classroomId and o.clientId <> :clientId and os.endDate is null and o.leaveDateTime is null")
    long countCurrentByClassroomIdAndNotClientId(@Param("classroomId") Long classroomId, @Param("clientId") String clientId);

    @Query("select count(o) from RubruSessionParticipant o inner join o.rubruSession os where os.creatorId = :classroomOwnerId and o.clientId <> :clientId and os.endDate is null and o.leaveDateTime is null")
    long countCurrentByClassroomOwnerIdAndNotClientId(@Param("classroomOwnerId") Long classroomOwnerId, @Param("clientId") String clientId);
}
