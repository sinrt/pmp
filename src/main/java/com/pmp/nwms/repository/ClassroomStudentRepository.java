package com.pmp.nwms.repository;

import com.pmp.nwms.domain.ClassroomStudent;
import com.pmp.nwms.domain.ClassroomStudentId;
import com.pmp.nwms.model.ClassroomStudentModel;
import com.pmp.nwms.service.dto.ClassroomStudentDTO;
import com.pmp.nwms.service.listmodel.UserClassroomListModel;
import com.pmp.nwms.service.listmodel.WebinarClassroomStudentListModel;
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

@Repository
public interface ClassroomStudentRepository extends JpaRepository<ClassroomStudent, ClassroomStudentId> {

    @Transactional
    @Modifying
    @Query("delete from ClassroomStudent classroomstudent where classroomstudent.pk.classroom.id =:id")
    void deleteByClassroomId(@Param("id") Long id);

    @Transactional
    @Modifying
    @Query("delete from ClassroomStudent classroomstudent where classroomstudent.pk.student.id =:id")
    void deleteByStudentId(@Param("id") Long id);

    @Query("select count(o) from ClassroomStudent o where o.pk.student.id = :studentId and o.pk.classroom.id <> :classroomId and o.pk.classroom.course.id = :courseId")
    long countOtherCourseClassroomStudent(@Param("courseId") Long courseId, @Param("classroomId") Long classroomId, @Param("studentId") Long studentId);

    @Query(value = "select " +
        " o.pk.classroom.id as classroomId, " +
        " s.id as userId, " +
        " s.login as login, " +
        " s.firstName as firstName, " +
        " s.lastName as lastName, " +
        " o.authorityName as authorityName, " +
        " o.needsLogin as needsLogin, " +
        " o.maxUseCount as maxUseCount, " +
        " o.token as token, " +
        " o.dynamicStudent as dynamicStudent " +
        " from ClassroomStudent o inner join o.pk.student s where o.pk.classroom.id = :classroomId",
        countQuery = "select count(o) from ClassroomStudent o where o.pk.classroom.id = :classroomId")
    Page<WebinarClassroomStudentListModel> findAllByClassroom(@Param("classroomId") Long classroomId, Pageable pageable);

    @Query("select count(o) from ClassroomStudent o where o.pk.classroom.id = :classroomId and o.pk.student.id = :userId and o.token = :token ")
    long countByTokenAndClassroomAndStudent(@Param("token") String token, @Param("classroomId") Long classroomId, @Param("userId") Long userId);

    @Query("select o from ClassroomStudent o where o.token = :token and o.pk.classroom.id = :classroomId and o.authorityName in :expectedAuthorityNames")
    Optional<ClassroomStudent> findByTokenAndClassroom(@Param("token") String token, @Param("classroomId") Long classroomId, @Param("expectedAuthorityNames") List<String> expectedAuthorityNames);

    @Query("select o from ClassroomStudent o where o.token is null")
    List<ClassroomStudent> findAllByEmptyToken();

    @Query("select o from ClassroomStudent o where o.pk.classroom.id = :classroomId and o.pk.student.id in :studentIds")
    List<ClassroomStudent> findAllByClassroomAndStudentIds(@Param("classroomId") Long classroomId, @Param("studentIds") List<Long> studentIds);

    @Modifying
    @Query("delete  from ClassroomStudent o where o.pk.student.id = :studentId and o.pk.classroom.id in :classroomIds")
    void deleteByClassroomIdsAndStudentId(@Param("classroomIds") List<Long> classroomIds, @Param("studentId") Long studentId);

    @Query("select " +
        " c.id as id, " +
        " c.name as name, " +
        " c.startDateTime as startDateTime, " +
        " c.finishDateTime as finishDateTime, " +
        " c.sessionUuidName as sessionUuidName, " +
        " cr.teacherPan as teacherPan, " +
        " c.useEnterToken as useEnterToken, " +
        " o.authorityName as authorityName, " +
        " o.token as token, " +
        " c.creator.id as creatorId " +
        " from ClassroomStudent o inner join o.pk.classroom c inner join c.course cr inner join o.pk.student s where s.id = :studentId and o.authorityName <> 'notexist' " +
        " and c.startDateTime <= :checkDate and c.finishDateTime >= :checkDate ")
    List<UserClassroomListModel> findUserActiveClassroomListModels(@Param("studentId") Long studentId, @Param("checkDate") Date checkDate);

    @Query("select " +
        " c.id as id, " +
        " c.name as name, " +
        " c.startDateTime as startDateTime, " +
        " c.finishDateTime as finishDateTime, " +
        " c.sessionUuidName as sessionUuidName, " +
        " cr.teacherPan as teacherPan, " +
        " c.useEnterToken as useEnterToken, " +
        " o.authorityName as authorityName, " +
        " o.token as token, " +
        " c.creator.id as creatorId " +
        " from ClassroomStudent o inner join o.pk.classroom c inner join c.course cr inner join o.pk.student s where s.id = :studentId and o.authorityName <> 'notexist' " +
        " and (c.startDateTime >= :checkDate or c.finishDateTime <= :checkDate) ")
    List<UserClassroomListModel> findUserInactiveClassroomListModels(@Param("studentId") Long studentId, @Param("checkDate") Date checkDate);

    @Query("select o from ClassroomStudent o where o.pk.classroom.id = :classroomId and o.pk.student.id = :studentId")
    Optional<ClassroomStudent> findByClassroomAndStudentId(@Param("classroomId") Long classroomId, @Param("studentId") Long studentId);

    @Query("select " +
        " c.id as classroomId, " +
        " c.name as classroomName, " +
        " c.sessionUuidName as classroomSessionUuidName, " +
        " o.authorityName as authorityName, " +
        " o.needsLogin as needsLogin, " +
        " o.maxUseCount as maxUseCount, " +
        " o.token as token, " +
        " c.creator.id as creatorId " +
        " from ClassroomStudent o inner join o.pk.classroom c where c.course.id = :courseId and o.pk.student.id = :studentId")
    List<ClassroomStudentDTO> findClassroomStudentsUsingCourseIdAndStudentId(@Param("courseId") Long courseId, @Param("studentId") Long studentId);

    @Modifying
    @Query("delete from ClassroomStudent  cs where cs.pk.student.id = :studentId")
    void deleteClassroomStudentsByStudentId(@Param("studentId") Long studentId);

    @Query("select " +
        " o.pk.classroom.id as classroomId, " +
        " o.pk.student.id as studentId, " +
        " o.authorityName as authorityName, " +
        " o.fullName as fullName, " +
        " o.needsLogin as needsLogin, " +
        " o.token as token, " +
        " o.maxUseCount as maxUseCount, " +
        " o.createdBy as createdBy, " +
        " o.createdDate as createdDate, " +
        " o.lastModifiedBy as lastModifiedBy, " +
        " o.lastModifiedDate as lastModifiedDate, " +
        " o.version as version " +
        " from ClassroomStudent o where o.pk.student.id = :studentId")
    List<ClassroomStudentModel> findClassroomStudentModelsUsingStudentId(@Param("studentId") Long studentId);

    @Query(value = "select cs1.student_id from classroom_students cs1 " +
        " left outer join classroom_students cs2 on (cs1.student_id = cs2.student_id and cs1.classroom_id != cs2.classroom_id) " +
        " where cs1.classroom_id = :classroomId and cs2.classroom_id is null", nativeQuery = true)
    List<Number> findSingleClassUserIdsUsingClassroomId(@Param("classroomId") Long classroomId);

    @Query("select " +
        " o.pk.classroom.id as classroomId, " +
        " o.pk.student.id as studentId, " +
        " o.authorityName as authorityName, " +
        " o.fullName as fullName, " +
        " o.needsLogin as needsLogin, " +
        " o.token as token, " +
        " o.maxUseCount as maxUseCount, " +
        " o.createdBy as createdBy, " +
        " o.createdDate as createdDate, " +
        " o.lastModifiedBy as lastModifiedBy, " +
        " o.lastModifiedDate as lastModifiedDate, " +
        " o.version as version " +
        "from ClassroomStudent  o where o.pk.classroom.id = :classroomId")
    List<ClassroomStudentModel> findClassroomStudentModelsUsingClassroomId(@Param("classroomId") Long classroomId);

    @Query("select " +
        " o.pk.classroom.id as classroomId, " +
        " o.pk.student.id as studentId, " +
        " o.authorityName as authorityName, " +
        " o.fullName as fullName, " +
        " o.needsLogin as needsLogin, " +
        " o.token as token, " +
        " o.maxUseCount as maxUseCount, " +
        " o.createdBy as createdBy, " +
        " o.createdDate as createdDate, " +
        " o.lastModifiedBy as lastModifiedBy, " +
        " o.lastModifiedDate as lastModifiedDate, " +
        " o.version as version " +
        "from ClassroomStudent o inner join o.pk.classroom c where c.course.id = :courseId")
    List<ClassroomStudentModel> findClassroomStudentModelsUsingCourseId(@Param("courseId") Long courseId);


    @Modifying
    @Query(value = "delete from classroom_students where classroom_id in (select id from classroom where course_id = :courseId)", nativeQuery = true)
    void deleteUsingCourseId(@Param("courseId") Long courseId);

    @Query("select o.pk.student.id from ClassroomStudent o where o.token = :token")
    Long findStudentIdUsingToken(@Param("token") String token);
}
