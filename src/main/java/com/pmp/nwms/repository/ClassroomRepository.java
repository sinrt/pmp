package com.pmp.nwms.repository;

import com.pmp.nwms.domain.Classroom;
import com.pmp.nwms.service.dto.ClassroomCheckStatusDTO;
import com.pmp.nwms.service.dto.ClassroomInfoDto;
import com.pmp.nwms.service.listmodel.ClassroomListModel;
import com.pmp.nwms.service.listmodel.UserClassroomListModel;
import com.pmp.nwms.web.rest.vm.ClassroomStudentInfoVM;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Classroom entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClassroomRepository extends JpaRepository<Classroom, Long> {
    String GET_LOAN_REPORTS = "SELECT * FROM loan_report  WHERE product = :product";

    @Query("select classroom from Classroom classroom where classroom.master.login = ?#{principal.username}")
    List<Classroom> findByMasterIsCurrentUser();

    @Query(value = "select distinct classroom from Classroom classroom left join fetch classroom.classroomStudents",
        countQuery = "select count(distinct classroom) from Classroom classroom")
    Page<Classroom> findAllWithEagerRelationships(Pageable pageable);

    @Query(value = "select distinct classroom from Classroom classroom left join fetch classroom.classroomStudents")
    List<Classroom> findAllWithEagerRelationships();

    @Query("select classroom from Classroom classroom left join fetch classroom.classroomStudents where classroom.id =:id")
    Optional<Classroom> findOneWithEagerRelationships(@Param("id") Long id);


    @Query("select classroom from Classroom classroom left join fetch classroom.classroomStudents classroomStudents left join fetch classroomStudents.pk.student student  where student.id=:id")
    List<Classroom> findUserClassromsJpql(@Param("id") Long id);

    @Query("select classroom from Classroom classroom inner join fetch classroom.classroomStudents classroomStudents inner join fetch classroomStudents.pk.student student  where student.id= ?1 ")
    Classroom findClassByUserIdAndDate(Long userId, Date currentDate);

    @Query("select distinct classroom from Classroom classroom inner join fetch classroom.classroomStudents classroomStudents inner join fetch classroomStudents.pk.student student where classroom.master.id=:id ")
    List<Classroom> findMasterClassrooms(@Param("id") Long id);

    @Query(value = "select distinct * from classroom where id in (select classroom_id from classroom_students where student_id=:id and authority_name != 'notexist') ", nativeQuery = true)
    List<Classroom> findUserClassroms(@Param("id") Long id);


    //todo Sina: The key-word BINARY is for making mysql search case sensitive, do not remove it.
    //todo Hosin: Wow, Thanks.
    @Query(value = "select * from classroom where BINARY session_uuid_name =:name ", nativeQuery = true)
    Optional<Classroom> findClassroomByName(@Param("name") String name);

    @Transactional
    @Modifying
    @Query("delete from Classroom classroom where classroom.sessionUuidName = :name")
    void deleteClassroomByName(@Param("name") String name);

    @Query("select classroom from Classroom classroom left join fetch classroom.classroomStudents classroomStudents left join fetch classroomStudents.pk.student student  where classroom.sessionUuidName=:uuid ")
    Optional<Classroom> findClassroomByUUID(@Param("uuid") String uuid);

    @Transactional
    @Modifying
    @Query("delete from Classroom classroom  where classroom.id=:id ")
    void deleteClassroomById(@Param("id") Long id);


    @Query("select " +
        " o.id as id, " +
        " o.name as name, " +
        " o.sessionUuidName as sessionUuidName, " +
        " o.guestSession as guestSession, " +
        " o.guestSessionReqPass as guestSessionReqPass, " +
        " o.guestPassword as guestPassword, " +
        " o.startDateTime as startDateTime, " +
        " o.finishDateTime as finishDateTime, " +
        " o.connectionType as connectionType, " +
        " o.resolution as resolution, " +
        " o.frameRate as frameRate, " +
        " o.lock as lock " +
        " from Classroom o inner join o.course c where c.creator.id=:userId and c.teacherPan=:teacherPan")
    List<ClassroomListModel> findByCreatorAndGStatus(@Param("userId") Long userId, @Param("teacherPan") boolean teacherPan);

    @Query("select count(o) from Classroom o where o.id = :id and o.creator.id=:userId")
    long countByIdAndCreator(@Param("id") Long id, @Param("userId") Long userId);

    @Query("select count(o) from Classroom o where o.creator.id=:userId")
    long countByCreator(@Param("userId") Long userId);


    @Query(value = "select classroom from Classroom classroom where classroom.course.id = :courseId",
        countQuery = "select count(classroom) from Classroom classroom where classroom.course.id = :courseId")
    Page<Classroom> findAllByCourseId(@Param("courseId") Long courseId, Pageable pageable);

    @Query("select classroom from Classroom classroom where classroom.course.id = :courseId")
    List<Classroom> findAllByCourseId(@Param("courseId") Long courseId);

    @Query("select " +
        " s.login as login, " +
        " s.firstName as firstName, " +
        " s.lastName as lastName, " +
        " cs.authorityName as authorityName, " +
        " cs.needsLogin as needsLogin, " +
        " cs.maxUseCount as maxUseCount, " +
        " o.name as sessionName, " +
        " o.sessionUuidName as sessionUuidName, " +
        " cs.token as token, " +
        " o.useEnterToken as useEnterToken, " +
        " c.teacherPan as teacherPan " +
        " from Classroom o inner join o.course c inner join o.classroomStudents cs inner join cs.pk.student s where o.id = :classroomId and cs.authorityName <> 'notexist' ")
    List<ClassroomStudentInfoVM> getClassroomStudentInfoVMs(@Param("classroomId") Long classroomId);

    @Query("select o.id from Classroom o where o.course.id=:courseId")
    List<Long> findIdsByCourseId(@Param("courseId") Long courseId);

    @Query(value = "select count(id) from classroom where BINARY name = :name or session_uuid_name =:name ", nativeQuery = true)
    long countByName(@Param("name") String name);

    @Query(value = "select " +
        " o.id as id, " +
        " o.name as name, " +
        " o.guest_session as guestSession, " +
        " c.teacher_pan as teacherPan, " +
        " o.use_enter_token as useEnterToken, " +
        " o.creator_id as creatorId, " +
        " o.start_time as startDateTime, " +
        " o.finish_time as finishDateTime, " +
        " o.max_user_count as maxUserCount, " +
        " o.hide_global_chat as hideGlobalChat, " +
        " o.hide_private_chat as hidePrivateChat, " +
        " o.hide_participants_list as hideParticipantsList, " +
        " o.disable_file_transfer as disableFileTransfer, " +
        " o.hide_sound_sensitive as hideSoundSensitive, " +
        " o.hide_publish_permit as hidePublishPermit, " +
        " o.enable_subscriber_direct_enter as enableSubscriberDirectEnter, " +
        " o.publisher_must_enter_first as publisherMustEnterFirst, " +
        " o.jhi_lock as locked, " +
        " o.is_guest_with_subscriber_role as isGuestWithSubscriberRole, " +
        " o.hide_screen as hideScreen, " +
        " o.hide_whiteboard as hideWhiteboard, " +
        " o.hide_slide as hideSlide, " +
        " o.recording_mode as recordingMode, " +
        " o.moderator_auto_login as moderatorAutoLogin, " +
        " o.secret_key as secretKey, " +
        " o.session_active as sessionActive, " +
        " o.signal_session_end as signalSessionEnd, " +
        " o.return_url as returnUrl, " +
        " o.outer_manage as outerManage, " +
        " o.non_manager_enter_sound_off as nonManagerEnterSoundOff, " +
        " o.non_manager_enter_video_off as nonManagerEnterVideoOff " +
        " from classroom o inner join course c on o.course_id = c.id where BINARY o.session_uuid_name = :sessionUuidName", nativeQuery = true)
    Optional<ClassroomCheckStatusDTO> findClassroomCheckStatusDTOUsingUUID(@Param("sessionUuidName") String sessionUuidName);

    @Query("select " +
        " c.id as id, " +
        " c.name as name, " +
        " c.startDateTime as startDateTime, " +
        " c.finishDateTime as finishDateTime, " +
        " c.sessionUuidName as sessionUuidName, " +
        " cr.teacherPan as teacherPan, " +
        " c.useEnterToken as useEnterToken, " +
        " c.creator.id as creatorId " +
        " from Classroom c inner join c.course cr where c.creator.id = :userId  " +
        " and cr.teacherPan = :teacherPan ")
    List<UserClassroomListModel> findCreatorClassrooms(@Param("userId") Long userId, @Param("teacherPan") boolean teacherPan);

    @Query("select " +
        " o.id as id, " +
        " c.id as courseId, " +
        " c.creator.id as courseCreatorId, " +
        " o.creator.id as classroomCreatorId " +
        " from Classroom o inner join o.course c where o.id = :classroomId")
    Optional<ClassroomInfoDto> findClassroomInfoDto(@Param("classroomId") Long classroomId);

    @Query(value = "select id from classroom where BINARY name = :name or session_uuid_name =:name ", nativeQuery = true)
    Optional<Long> findIdUsingName(@Param("name") String name);

    @Query("select o from Classroom o where o.course.id = :courseId")
    List<Classroom> findByCourseId(@Param("courseId") Long courseId);

    @Query("select count(o) from Classroom o where o.master.id = :userId or o.creator.id = :userId or o.lastModifier.id = :userId")
    long countByMasterOrCreatorOrLastModifier(@Param("userId") Long userId);

    @Modifying
    @Query("delete from Classroom o where o.course.id = :courseId")
    void deleteByCourseId(@Param("courseId") Long courseId);

    @Query(value = "select count(id) from classroom where BINARY session_uuid_name = :sessionUuidName and id != :notForId ", nativeQuery = true)
    long countBySessionUuidNameAndNotForId(@Param("sessionUuidName") String sessionUuidName, @Param("notForId") Long notForId);

    @Query(value = "select count(id) from classroom where BINARY name = :name and course_id = :courseId and id != :notForId ", nativeQuery = true)
    long countByNameAndCourseAndNotForId(@Param("name") String name, @Param("courseId") Long courseId, @Param("notForId") Long notForId);

    @Modifying
    @Query("update Classroom o set o.sessionActive = :newSessionActive where o.id = :id")
    void updateSessionActiveStatus(@Param("id") Long id, @Param("newSessionActive") boolean newSessionActive);
}
