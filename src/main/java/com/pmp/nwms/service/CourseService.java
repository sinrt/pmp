package com.pmp.nwms.service;

import com.pmp.nwms.domain.Course;

import com.pmp.nwms.service.dto.CourseStudentDTO;
import com.pmp.nwms.service.listmodel.CourseListModel;
import com.pmp.nwms.service.listmodel.CourseStudentListModel;
import com.pmp.nwms.web.rest.vm.CourseBatchUserVM;
import com.pmp.nwms.web.rest.vm.CourseSimpleVM;
import com.pmp.nwms.web.rest.vm.CourseUserVM;
import com.pmp.nwms.web.rest.vm.CourseVM;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Course.
 */
public interface CourseService {

    /**
     * Save a course.
     *
     * @param course the entity to updateClassroom
     * @return the persisted entity
     */
    Course save(Course course);

    /**
     * Get all the courses.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Course> findAll(Pageable pageable);

    List<Course> findByCreatorAndGStatus(Long id,boolean isGuestRoom);

    List<Course> findByCreatorAndTeacherPan(Long id,boolean teacherPan);


    /**
     * Get the "id" course.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Course> findOne(Long id);

    Optional<Course> findOneByName(String name);

    /**
     * Delete the "id" course.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    @Transactional
    Long saveCourse(CourseVM vm);

    CourseVM getCourseVM(Long courseId);

    Page<CourseVM> findAllCourseVMs(Pageable pageable);


    @Transactional
    Long createNewCourseUser(CourseUserVM vm);

    @Transactional
    Long updateCourseUser(CourseUserVM vm);

    @Transactional
    List<Long> addBatchUsers(CourseBatchUserVM vm);

    @Transactional
    boolean deleteCourseUser(Long courseId, Long userId);

    List<CourseListModel> findCreatorAndTeacherPan(boolean isTeacherPan);

    @Transactional
    Long simpleSave(CourseSimpleVM course);

    List<CourseStudentListModel> getCourseStudents(Long courseId);

    CourseStudentDTO getCourseStudent(Long courseId, Long studentId);

    @Transactional
    void fullDeleteCourse(Long courseId);
}
