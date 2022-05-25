package com.pmp.nwms.repository;

import com.pmp.nwms.domain.ClassroomSurvey;
import com.pmp.nwms.domain.enums.SurveyStatus;
import com.pmp.nwms.service.listmodel.ClassroomSurveyListModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassroomSurveyRepository  extends JpaRepository<ClassroomSurvey, Long> {
    List<ClassroomSurvey> findAllByClassroomIdAndStatus(Long classroomId, SurveyStatus status);

    @Query("select " +
        " o.id as id, " +
        " o.question as question, " +
        " o.surveyType as surveyType, " +
        " o.status as status " +
        " from ClassroomSurvey o where o.classroom.id = :classroomId and o.status not in :statuses")
    List<ClassroomSurveyListModel> getClassroomSurveyListModelsNotInStatuses(@Param("classroomId") Long classroomId, @Param("statuses") List<SurveyStatus> statuses);

    @Query("select " +
        " o.id as id, " +
        " o.question as question, " +
        " o.surveyType as surveyType, " +
        " o.status as status " +
        " from ClassroomSurvey o where o.classroom.id = :classroomId and o.status in :statuses")
    List<ClassroomSurveyListModel> getClassroomSurveyListModelsInStatuses(@Param("classroomId") Long classroomId, @Param("statuses") List<SurveyStatus> statuses);
}
