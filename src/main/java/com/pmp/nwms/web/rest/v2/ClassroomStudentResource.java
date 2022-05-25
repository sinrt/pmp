package com.pmp.nwms.web.rest.v2;

import com.pmp.nwms.service.CourseService;
import com.pmp.nwms.service.dto.CourseStudentDTO;
import com.pmp.nwms.service.listmodel.CourseStudentListModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("classroomStudentResourceV2")
@RequestMapping("/api/v2")
public class ClassroomStudentResource {

    @Autowired
    private CourseService courseService;

    @PostMapping("/course/student/list/{courseId}")
    public ResponseEntity<List<CourseStudentListModel>> getCourseStudents(@PathVariable("courseId") Long courseId) {
        List<CourseStudentListModel> result = courseService.getCourseStudents(courseId);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/course/{courseId}/student/{studentId}")
    public ResponseEntity<CourseStudentDTO> getCourseStudent(@PathVariable("courseId") Long courseId, @PathVariable("studentId") Long studentId) {
        CourseStudentDTO result = courseService.getCourseStudent(courseId, studentId);
        return ResponseEntity.ok().body(result);
    }
}
