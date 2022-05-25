package com.pmp.nwms.web.rest;

import com.pmp.nwms.domain.Course;
import com.pmp.nwms.domain.User;
import com.pmp.nwms.repository.CourseRepository;
import com.pmp.nwms.repository.UserRepository;
import com.pmp.nwms.security.AuthoritiesConstants;
import com.pmp.nwms.security.NwmsUser;
import com.pmp.nwms.service.CourseService;
import com.pmp.nwms.service.UserService;
import com.pmp.nwms.util.UserUtil;
import com.pmp.nwms.web.rest.errors.BadRequestAlertException;
import com.pmp.nwms.web.rest.errors.CourseAlreadyUsedException;
import com.pmp.nwms.web.rest.util.HeaderUtil;
import com.pmp.nwms.web.rest.util.PaginationUtil;
import com.pmp.nwms.web.rest.vm.CourseBatchUserVM;
import com.pmp.nwms.web.rest.vm.CourseUserVM;
import com.pmp.nwms.web.rest.vm.CourseVM;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Course.
 */
@RestController
@RequestMapping("/api")
public class CourseResource {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    private final Logger log = LoggerFactory.getLogger(CourseResource.class);

    private static final String ENTITY_NAME = "course";

    private final CourseService courseService;

    private final CourseRepository courseRepository;

    public CourseResource(CourseService courseService, CourseRepository courseRepository) {
        this.courseService = courseService;
        this.courseRepository = courseRepository;
    }

    /**
     * POST  /courses : Create a new course.
     *
     * @param course the course to create
     * @return the ResponseEntity with status 201 (Created) and with body the new course, or with status 400 (Bad Request) if the course has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/courses")
    public ResponseEntity<Course> createCourse(@Valid @RequestBody Course course) throws URISyntaxException {
        log.debug("REST request to updateClassroom Course : {}", course);
        if (course.getId() != null) {
            throw new BadRequestAlertException("A new course cannot already have an ID", ENTITY_NAME, "idexists");
        } else if (courseRepository.findByTitle(course.getTitle()).isPresent()) {
            throw new CourseAlreadyUsedException();
        }

        User user = UserUtil.getCurrentUserEntity_(userRepository);

        course.setCreator(user);
        course.setModifier(user);
        Course result = courseService.save(course);
        return ResponseEntity.created(new URI("/api/courses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /courses : Updates an existing course.
     *
     * @param course the course to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated course,
     * or with status 400 (Bad Request) if the course is not valid,
     * or with status 500 (Internal Server Error) if the course couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/courses")
    public ResponseEntity<Course> updateCourse(@Valid @RequestBody Course course) throws URISyntaxException {
        log.debug("REST request to update Course : {}", course);
        if (course.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        } /*else if (courseRepository.findByTitle(course.getTitle()).isPresent()) {
            throw new CourseAlreadyUsedException();
        }*/

        User user = UserUtil.getCurrentUserEntity_(userRepository);
        course.setModifier(user);
        Course result = courseService.save(course);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, course.getId().toString()))
            .body(result);
    }

    /**
     * GET  /courses : get all the courses.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of courses in body
     */
    @GetMapping("/courses")
    public ResponseEntity<List<Course>> getAllCourses(Pageable pageable) {
        log.debug("REST request to get a page of Courses");
        Page<Course> page = courseService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/courses");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/courses/bycreator/{isGuestRoom}")
    public ResponseEntity<List<Course>> getAllTeacherCourses(@PathVariable boolean isGuestRoom) {
        log.debug("REST request to get a page of Courses");
        NwmsUser user = UserUtil.getCurrentUser();
        List<Course> page = courseService.findByCreatorAndGStatus(user.getId(), isGuestRoom);
        //HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(null, "/api/courses");
//        return ResponseEntity.ok().headers(headers).body(page);
        return ResponseEntity.ok().body(page);
    }

    /**
     * GET  /courses/:id : get the "id" course.
     *
     * @param id the id of the course to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the course, or with status 404 (Not Found)
     */
    @GetMapping("/courses/{id}")
    public ResponseEntity<Course> getCourse(@PathVariable Long id) {
        log.debug("REST request to get Course : {}", id);
        Optional<Course> course = courseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(course);
    }

    /**
     * DELETE  /courses/:id : delete the "id" course.
     *
     * @param id the id of the course to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/courses/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        log.debug("REST request to delete Course : {}", id);
        courseService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }


    /**
     * creates or updates the given course. if the input has id, it will update it, otherwise, it will create a new course.
     * If in the edit mode, the course does not belong to the current user, it will throw an exception.
     *
     * @param vm
     * @return
     */
    @PostMapping("/webinar/course/save")
    @Secured(AuthoritiesConstants.API)
    public ResponseEntity<Long> saveCourse(@Valid @RequestBody CourseVM vm) {
        Long id = courseService.saveCourse(vm);
        return ResponseEntity.ok().body(id);
    }

    /**
     * returns a course with the given courseId.
     * If the course does not belong to the current user, it will throw an exception.
     *
     * @param courseId
     * @return
     */
    @GetMapping("/webinar/course/{courseId}")
    @Secured(AuthoritiesConstants.API)
    public ResponseEntity<CourseVM> getCourseVM(@PathVariable("courseId") Long courseId) {
        CourseVM vm = courseService.getCourseVM(courseId);
        return ResponseEntity.ok().body(vm);
    }


    /**
     * returns a page of user courses based on the input pageable
     *
     * @param pageable
     * @return
     */
    @PostMapping("/webinar/course/list")
    @Secured(AuthoritiesConstants.API)
    public ResponseEntity<List<CourseVM>> getCourseVM(Pageable pageable) {
        Page<CourseVM> page = courseService.findAllCourseVMs(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/webinar/course/list");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @PostMapping("/webinar/panel/course/users")
    public ResponseEntity<Long> addCourseUser(@Valid @RequestBody CourseUserVM vm) {
        if (vm.getId() != null) {
            throw new BadRequestAlertException("A new user cannot have an ID", "User", "idexists");
        }
        Long userId = courseService.createNewCourseUser(vm);
        return ResponseEntity.ok().body(userId);
    }

    @PutMapping("/webinar/panel/course/users")
    public ResponseEntity<Long> editCourseUser(@Valid @RequestBody CourseUserVM vm) {
        if (vm.getId() == null) {
            throw new BadRequestAlertException("Invalid id", "User", "idnull");
        }
        Long userId = courseService.updateCourseUser(vm);
        return ResponseEntity.ok().body(userId);
    }

    @PostMapping("/course/batch/add/user")
    public ResponseEntity<List<Long>> addBatchUsers(@Valid @RequestBody CourseBatchUserVM vm){
        List<Long> userIds = courseService.addBatchUsers(vm);
        return ResponseEntity.ok().body(userIds);
    }

    @DeleteMapping("/course/delete/user/{courseId}/{userId}")
    public ResponseEntity<Boolean> deleteCourseUser(@PathVariable("courseId") Long courseId, @PathVariable("userId") Long userId) {
        boolean result = courseService.deleteCourseUser(courseId, userId);
        return ResponseEntity.ok().body(result);
    }
}
