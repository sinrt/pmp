package com.pmp.nwms.web.rest.v2;

import com.pmp.nwms.domain.Course;
import com.pmp.nwms.domain.User;
import com.pmp.nwms.service.CourseService;
import com.pmp.nwms.service.listmodel.CourseListModel;
import com.pmp.nwms.util.UserUtil;
import com.pmp.nwms.web.rest.errors.BadRequestAlertException;
import com.pmp.nwms.web.rest.util.HeaderUtil;
import com.pmp.nwms.web.rest.vm.CourseSimpleVM;
import com.pmp.nwms.web.rest.vm.CourseVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.List;

@RestController("courseResourceV2")
@RequestMapping("/api/v2")
public class CourseResource {
    private final Logger log = LoggerFactory.getLogger(CourseResource.class);

    @Autowired
    private CourseService courseService;


    @GetMapping("/courses/bycreator/{isTeacherPan}")
    public ResponseEntity<List<CourseListModel>> getAllTeacherCourses(@PathVariable("isTeacherPan") boolean isTeacherPan) {
        log.debug("REST request to get a page of Courses " + isTeacherPan);
        List<CourseListModel> page = courseService.findCreatorAndTeacherPan(isTeacherPan);
        return ResponseEntity.ok().body(page);
    }

    @PostMapping("/courses")
    public ResponseEntity<Long> createCourse(@Valid @RequestBody CourseSimpleVM course) throws URISyntaxException {
        log.debug("REST request to update Course : {}", course);
        if (course.getId() != null) {
            throw new BadRequestAlertException("A new course cannot already have an ID", "Course", "idexists");
        }

        Long result = courseService.simpleSave(course);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("Course", result.toString()))
            .body(result);
    }

    @PutMapping("/courses")
    public ResponseEntity<Long> updateCourse(@Valid @RequestBody CourseSimpleVM course) throws URISyntaxException {
        log.debug("REST request to update Course : {}", course);
        if (course.getId() == null) {
            throw new BadRequestAlertException("Invalid id", "Course", "idnull");
        }

        Long result = courseService.simpleSave(course);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("Course", result.toString()))
            .body(result);
    }

    @DeleteMapping("/course/full/delete/{courseId}")
    public ResponseEntity<Boolean> fullDeleteCourse(@PathVariable("courseId") Long courseId){
        courseService.fullDeleteCourse(courseId);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityDeletionAlert("Course", courseId.toString()))
            .body(true);
    }

}
