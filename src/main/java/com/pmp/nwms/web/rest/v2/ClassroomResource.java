package com.pmp.nwms.web.rest.v2;

import com.codahale.metrics.annotation.Timed;
import com.pmp.nwms.service.ClassroomService;
import com.pmp.nwms.service.listmodel.UserClassroomDataListModel;
import com.pmp.nwms.service.listmodel.UserClassroomListModel;
import com.pmp.nwms.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController("classroomResourceV2")
@RequestMapping("/api/v2")
public class ClassroomResource {
    private final Logger log = LoggerFactory.getLogger(com.pmp.nwms.web.rest.ClassroomResource.class);

    @Autowired
    private ClassroomService classroomService;

    @GetMapping("/classrooms/user/{active}")
    @Timed
    public ResponseEntity<List<UserClassroomDataListModel>> getCurrentUserClasses(@PathVariable("active") boolean active) {
        List<UserClassroomDataListModel> userClasses = classroomService.findUserClassroomListModels(active);
        return ResponseEntity.ok().headers(HeaderUtil.createAlert("server-date", new Date().toInstant().toString())).body(userClasses);
    }

    @GetMapping("/classrooms/bycreator/{isTeacherPan}")
    @Timed
    public ResponseEntity<List<UserClassroomDataListModel>> getCreatorClassrooms(@PathVariable("isTeacherPan") boolean isTeacherPan) {
        List<UserClassroomDataListModel> userClasses = classroomService.getCreatorClassrooms(isTeacherPan);
        return ResponseEntity.ok().headers(HeaderUtil.createAlert("server-date", new Date().toInstant().toString())).body(userClasses);
    }

    @DeleteMapping("/classroom/full/delete/{classroomId}")
    public ResponseEntity<Boolean> fullDeleteClassroom(@PathVariable("classroomId") Long classroomId){
        classroomService.fullDeleteClassroom(classroomId);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityDeletionAlert("Classroom", classroomId.toString()))
            .body(true);
    }
}
