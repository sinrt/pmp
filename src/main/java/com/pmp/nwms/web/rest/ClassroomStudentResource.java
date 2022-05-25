package com.pmp.nwms.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pmp.nwms.security.AuthoritiesConstants;
import com.pmp.nwms.service.ClassroomStudentService;
import com.pmp.nwms.service.dto.WebinarClassroomStudentDTO;
import com.pmp.nwms.service.dto.WebinarClassroomStudentResultDto;
import com.pmp.nwms.service.listmodel.WebinarClassroomStudentListModel;
import com.pmp.nwms.web.rest.util.PaginationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ClassroomStudentResource {

    @Autowired
    private ClassroomStudentService classroomStudentService;

    /**
     * creates or updates the given ClassroomStudent. if the input has id, it will update it, otherwise, it will create a new ClassroomStudent.
     * If in the edit mode, the classroomStudent classroom does not belong to the current user, it will throw an exception.
     *
     * @param dto
     * @return the generated token for classroomStudent
     */
    @PostMapping("/webinar/classroom/student/save")
    @Timed
    @Secured(AuthoritiesConstants.API)
    public ResponseEntity<WebinarClassroomStudentResultDto> saveWebinarClassroomStudent(@Valid @RequestBody WebinarClassroomStudentDTO dto) {
        WebinarClassroomStudentResultDto result = classroomStudentService.saveWebinarClassroomStudent(dto);
        return ResponseEntity.ok().body(result);
    }


    /**
     * returns the classroom with the given classroomId.
     * If the classroom does not belong to the current user, it will throw an exception.
     *
     * @param classroomStudentId
     * @return
     */
    @DeleteMapping("/webinar/classroom/student/delete/{classroomId}/{studentId}")
    @Timed
    @Secured(AuthoritiesConstants.API)
    public ResponseEntity<Boolean> deleteWebinarClassroomStudent(@PathVariable("classroomId") Long classroomId, @PathVariable("studentId") Long studentId) {
        classroomStudentService.delete(classroomId, studentId);
        return ResponseEntity.ok().body(true);
    }

    /**
     * returns a page of classroom students based on the input pageable
     *
     * @param pageable
     * @return
     */
    @PostMapping("/webinar/classroom/student/list/{classroomId}")
    @Secured(AuthoritiesConstants.API)
    public ResponseEntity<List<WebinarClassroomStudentListModel>> getWebinarClassroomStudents(@PathVariable("classroomId") Long classroomId, Pageable pageable) {
        Page<WebinarClassroomStudentListModel> page = classroomStudentService.findWebinarClassroomStudentDTOsByClassroomId(classroomId, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/webinar/classroom/student/list/" + classroomId);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }


    @PostMapping("/student/data/fix")
    @Timed
    public ResponseEntity<Boolean> fixStudentsData() {
        classroomStudentService.fixStudentsData();
        return ResponseEntity.ok().body(true);
    }

}
