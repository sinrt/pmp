package com.pmp.nwms.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pmp.nwms.domain.enums.SurveyStatus;
import com.pmp.nwms.service.ClassroomSurveyService;
import com.pmp.nwms.service.dto.ClassroomSurveyAnswerReportDto;
import com.pmp.nwms.service.listmodel.ClassroomSurveyAnswerListModel;
import com.pmp.nwms.service.dto.ClassroomSurveyDTO;
import com.pmp.nwms.service.listmodel.ClassroomSurveyListModel;
import com.pmp.nwms.web.rest.errors.BadRequestAlertException;
import com.pmp.nwms.web.rest.vm.ClassroomSurveyAnsweringVM;
import com.pmp.nwms.web.rest.vm.ClassroomSurveyVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ClassroomSurveyResource {

    private static final Logger log = LoggerFactory.getLogger(ClassroomResource.class);

    @Autowired
    private ClassroomSurveyService classroomSurveyService;

    @PostMapping("/classroom/survey/save")
    @Timed
    public ResponseEntity<Long> createClassroomSurvey(@Valid @RequestBody ClassroomSurveyVM vm) {
        log.info("Post : /classroom/survey/save");
        if (vm.getId() != null) {
            throw new BadRequestAlertException("A new classroomSurvey cannot have an ID", "ClassroomSurvey", "idexists");
        }
        Long id = classroomSurveyService.saveClassroomSurvey(vm);
        return ResponseEntity.ok().body(id);
    }

    @PutMapping("/classroom/survey/save")
    @Timed
    public ResponseEntity<Long> updateClassroomSurvey(@Valid @RequestBody ClassroomSurveyVM vm) {
        log.info("Put : /classroom/survey/save");
        if (vm.getId() == null) {
            throw new BadRequestAlertException("Invalid id", "ClassroomSurvey", "idnull");
        }
        Long id = classroomSurveyService.saveClassroomSurvey(vm);
        return ResponseEntity.ok().body(id);
    }

    @GetMapping("/classroom/survey/{classroomSurveyId}")
    @Timed
    public ResponseEntity<ClassroomSurveyDTO> getClassroomSurvey(@PathVariable("classroomSurveyId") Long classroomSurveyId) {
        log.info("Get : /classroom/survey/" + classroomSurveyId);
        ClassroomSurveyDTO dto = classroomSurveyService.getClassroomSurveyVM(classroomSurveyId);
        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping("/classroom/survey/{classroomSurveyId}")
    @Timed
    public ResponseEntity<Boolean> deleteClassroomSurvey(@PathVariable("classroomSurveyId") Long classroomSurveyId) {
        log.info("Delete : /classroom/survey/" + classroomSurveyId);
        classroomSurveyService.deleteClassroomSurvey(classroomSurveyId);
        return ResponseEntity.ok().body(true);
    }

    @PostMapping("/classroom/survey/activate/{classroomSurveyId}")
    @Timed
    public ResponseEntity<Boolean> activateClassroomSurvey(@PathVariable("classroomSurveyId") Long classroomSurveyId) {
        log.info("Post : /classroom/survey/activate/" + classroomSurveyId);
        classroomSurveyService.changeStatusClassroomSurvey(classroomSurveyId, SurveyStatus.Activated, Collections.singletonList(SurveyStatus.Defining));
        return ResponseEntity.ok().body(true);
    }

    @PostMapping("/classroom/survey/deactivate/{classroomSurveyId}")
    @Timed
    public ResponseEntity<Boolean> deactivateClassroomSurvey(@PathVariable("classroomSurveyId") Long classroomSurveyId) {
        log.info("Post : /classroom/survey/deactivate/" + classroomSurveyId);
        classroomSurveyService.changeStatusClassroomSurvey(classroomSurveyId, SurveyStatus.Defining, Collections.singletonList(SurveyStatus.Activated));
        return ResponseEntity.ok().body(true);
    }

    @PostMapping("/classroom/survey/publish/{classroomId}")
    @Timed
    public ResponseEntity<Integer> publishClassroomSurveys(@PathVariable("classroomId") Long classroomId) {
        log.info("Post : /classroom/survey/publish/" + classroomId);
        int publishedCount = classroomSurveyService.publishClassroomSurveys(classroomId);
        return ResponseEntity.ok().body(publishedCount);
    }

    @PostMapping("/classroom/survey/unpublish/{classroomId}")
    @Timed
    public ResponseEntity<Integer> unpublishClassroomSurveys(@PathVariable("classroomId") Long classroomId) {
        log.info("Post : /classroom/survey/unpublish/" + classroomId);
        int unpublishedCount = classroomSurveyService.unpublishClassroomSurveys(classroomId);
        return ResponseEntity.ok().body(unpublishedCount);
    }

    @GetMapping("/classroom/survey/list/{classroomId}")
    @Timed
    public ResponseEntity<List<ClassroomSurveyListModel>> getClassroomSurveys(@PathVariable("classroomId") Long classroomId) {
        log.info("Get : /classroom/survey/list/" + classroomId);
        List<ClassroomSurveyListModel> result = classroomSurveyService.getClassroomSurveys(classroomId);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/classroom/survey/published/list/{classroomId}")
    @Timed
    public ResponseEntity<List<ClassroomSurveyDTO>> getClassroomPublishedSurveys(@PathVariable("classroomId") Long classroomId) {
        log.info("Get : /classroom/survey/published/list/" + classroomId);
        List<ClassroomSurveyDTO> result = classroomSurveyService.getClassroomPublishedSurveys(classroomId);
        return ResponseEntity.ok().body(result);
    }

    @PostMapping("/classroom/survey/answer/save")
    @Timed
    public ResponseEntity<Boolean> answerClassroomSurveys(@Valid @RequestBody ClassroomSurveyAnsweringVM vm) {
        log.info("Post : classroom/survey/answer/save");
        classroomSurveyService.answerClassroomSurveys(vm);
        return ResponseEntity.ok().body(true);
    }

    @GetMapping("/classroom/survey/answer/full/list/{classroomSurveyId}")
    @Timed
    public ResponseEntity<List<ClassroomSurveyAnswerListModel>> getClassroomSurveyFullAnswerList(@PathVariable("classroomSurveyId") Long classroomSurveyId) {
        log.info("Get : /classroom/survey/answer/full/list/" + classroomSurveyId);
        List<ClassroomSurveyAnswerListModel> result = classroomSurveyService.getClassroomSurveyFullAnswerList(classroomSurveyId);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/classroom/survey/answer/report/{classroomSurveyId}")
    @Timed
    public ResponseEntity<List<ClassroomSurveyAnswerReportDto>> getClassroomSurveyAnswerReport(@PathVariable("classroomSurveyId") Long classroomSurveyId) {
        log.info("Get : /classroom/survey/answer/report/" + classroomSurveyId);
        List<ClassroomSurveyAnswerReportDto> result = classroomSurveyService.getClassroomSurveyAnswerReport(classroomSurveyId);
        return ResponseEntity.ok().body(result);
    }


}
