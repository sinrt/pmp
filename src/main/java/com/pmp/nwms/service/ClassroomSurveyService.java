package com.pmp.nwms.service;

import com.pmp.nwms.domain.enums.SurveyStatus;
import com.pmp.nwms.service.dto.ClassroomSurveyAnswerReportDto;
import com.pmp.nwms.service.dto.ClassroomSurveyDTO;
import com.pmp.nwms.service.listmodel.ClassroomSurveyAnswerListModel;
import com.pmp.nwms.service.listmodel.ClassroomSurveyListModel;
import com.pmp.nwms.web.rest.vm.ClassroomSurveyAnsweringVM;
import com.pmp.nwms.web.rest.vm.ClassroomSurveyVM;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ClassroomSurveyService {
    @Transactional
    Long saveClassroomSurvey(ClassroomSurveyVM vm);

    ClassroomSurveyDTO getClassroomSurveyVM(Long classroomSurveyId);

    @Transactional
    void deleteClassroomSurvey(Long classroomSurveyId);

    @Transactional
    void changeStatusClassroomSurvey(Long classroomSurveyId, SurveyStatus newStatus, List<SurveyStatus> expectedStatus);

    @Transactional
    int publishClassroomSurveys(Long classroomId);

    @Transactional
    int unpublishClassroomSurveys(Long classroomId);

    List<ClassroomSurveyListModel> getClassroomSurveys(Long classroomId);

    List<ClassroomSurveyDTO> getClassroomPublishedSurveys(Long classroomId);

    @Transactional
    void answerClassroomSurveys(ClassroomSurveyAnsweringVM vm);

    List<ClassroomSurveyAnswerListModel> getClassroomSurveyFullAnswerList(Long classroomSurveyId);

    List<ClassroomSurveyAnswerReportDto> getClassroomSurveyAnswerReport(Long classroomSurveyId);
}
