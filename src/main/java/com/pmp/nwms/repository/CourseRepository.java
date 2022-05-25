package com.pmp.nwms.repository;

import com.pmp.nwms.domain.Course;
import com.pmp.nwms.service.dto.CourseInfoDto;
import com.pmp.nwms.service.listmodel.CourseListModel;
import com.pmp.nwms.service.listmodel.CourseStudentListModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Course entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    @Query("select course from Course course where course.creator.login = ?#{principal.username}")
    List<Course> findByCreatorIsCurrentUser();

    @Query("select course from Course course where course.modifier.login = ?#{principal.username}")
    List<Course> findByModifierIsCurrentUser();


    Optional<Course> findByTitle(String title);

    @Transactional
    @Modifying
    @Query("delete from Course course  where course.id=:id ")
    void deleteCourseById(@Param("id") Long id);

    @Transactional
    @Modifying
    @Query("select course from Course course  where course.creator.id=:id and course.teacherPan=:teacherPan")
    List<Course> findCourseByCreatorAndTeacherPan(@Param("id") Long id, @Param("teacherPan") Boolean teacherPan);

    @Query("select count(course) from Course course where course.creator.id = :creatorId and course.title = :title and course.id <> :notId")
    long countByTitle(@Param("title") String title, @Param("creatorId") Long creatorId, @Param("notId") Long notId);

    @Query("select course from Course course where course.creator.id = :creatorId")
    Page<Course> findAllByCreator(@Param("creatorId") Long creatorId, Pageable pageable);

    @Query("select o.id as id, o.title as title, o.description as description, o.teacherPan as teacherPan from Course o  where o.creator.id=:creatorId and o.teacherPan=:teacherPan")
    List<CourseListModel> findCourseListModelByCreatorAndTeacherPan(@Param("creatorId") Long creatorId, @Param("teacherPan") boolean teacherPan);

    @Query("select s.id as userId, " +
        " s.login as login, " +
        " s.firstName as firstName, " +
        " s.lastName as lastName " +
        " from Course o inner join o.students s where o.id = :courseId")
    List<CourseStudentListModel> getCourseStudents(@Param("courseId") Long courseId);

    @Query("select count(o) from Course o inner join o.students s where o.id = :courseId and s.id = :studentId and o.creator.id = :creatorId")
    long countCourseStudentByCreator(@Param("courseId") Long courseId, @Param("studentId") Long studentId, @Param("creatorId") Long creatorId);

    @Query("select o.id as id, o.creator.id as creatorId from Course o where o.id = :courseId")
    Optional<CourseInfoDto> findCourseInfoDto(@Param("courseId") Long courseId);

    @Modifying
    @Query(value = "delete from course_student where students_id = :studentId", nativeQuery = true)
    void deleteCourseStudentsUsingStudentId(@Param("studentId") Long studentId);

    @Query("select count(o) from Course o where o.creator.id = :userId or o.modifier.id = :userId")
    long countByCreatorOrModifier(@Param("userId") Long userId);

    @Query("select o.id from Course o inner join o.students s where s.id = :userId")
    List<Long> findCourseStudentIdsByUserId(@Param("userId")Long userId);

    @Query(value = " select cs1.students_id from course_student cs1 " +
        " left outer join course_student cs2 on (cs1.students_id = cs2.students_id and cs1.course_id != cs2.course_id) " +
        " where cs1.course_id = :courseId and cs2.course_id is null", nativeQuery = true)
    List<Number> findSingleCourseUserIdsUsingCourseId(@Param("courseId") Long courseId);

    @Query(value = "select cs1.students_id from course_student cs1 where cs1.course_id = :courseId", nativeQuery = true)
    List<Number> findCourseStudentIdsUsingCourseId(@Param("courseId") Long courseId);

    @Query("select o.teacherPan from Course o where o.id = :courseId")
    boolean findTeacherPanValueById(@Param("courseId")Long courseId);
}
