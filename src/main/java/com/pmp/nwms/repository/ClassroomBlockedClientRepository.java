package com.pmp.nwms.repository;

import com.pmp.nwms.domain.ClassroomBlockedClient;
import com.pmp.nwms.service.listmodel.ClassroomBlockedClientListModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassroomBlockedClientRepository extends JpaRepository<ClassroomBlockedClient, Long> {

    @Query("select count(o) from ClassroomBlockedClient o where o.classroom.id = :classroomId and o.clientId = :clientId")
    long countByClientIdAndClassroom(@Param("clientId") String clientId, @Param("classroomId") Long classroomId);

    @Query("select count(o) from ClassroomBlockedClient o where o.classroom.id = :classroomId and o.blockedUserId = :blockedUserId")
    long countByBlockedUserIdAndClassroom(@Param("blockedUserId") Long blockedUserId, @Param("classroomId") Long classroomId);

    @Query("select o.clientId as clientId, o.participantName as participantName, o.blockTime as blockTime from ClassroomBlockedClient o where o.classroom.id = :classroomId")
    List<ClassroomBlockedClientListModel> getClassroomBlockedClientListModels(@Param("classroomId") Long classroomId);

    @Modifying
    @Query("delete from ClassroomBlockedClient o where o.classroom.id = :classroomId and o.clientId in :clientIds")
    void deleteByClassroomIdAndClientIds(@Param("classroomId") Long classroomId, @Param("clientIds") List<String> clientIds);

    @Modifying
    @Query("delete from ClassroomBlockedClient o where o.classroom.id = :classroomId")
    void deleteByClassroomId(@Param("classroomId") Long classroomId);
}
