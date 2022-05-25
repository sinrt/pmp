package com.pmp.nwms.repository;

import com.pmp.nwms.domain.ClassroomSurveyAnswer;
import com.pmp.nwms.service.dto.ClassroomSurveyAnswerReportDto;
import com.pmp.nwms.service.listmodel.ClassroomSurveyAnswerListModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassroomSurveyAnswerRepository extends JpaRepository<ClassroomSurveyAnswer, Long> {
    @Query("select count(o) from ClassroomSurveyAnswer o where o.classroomSurvey.id in :classroomSurveyIds and o.clientId = :clientId")
    long countUsingClientIdAndClassroomSurveyIds(@Param("clientId") String clientId, @Param("classroomSurveyIds") List<Long> classroomSurveyIds);

    @Query("select count(o) from ClassroomSurveyAnswer o where o.classroomSurvey.id in :classroomSurveyIds and o.userId = :userId")
    long countUsingUserIdAndClassroomSurveyIds(@Param("userId")Long userId,@Param("classroomSurveyIds") List<Long> classroomSurveyIds);

    @Query("select " +
        " o.userId as userId, " +
        " o.clientId as clientId, " +
        " o.token as token, " +
        " o.answerDateTime as answerDateTime, " +
        " o.answerText as answerText, " +
        " so.id as selectedOptionId, " +
        " so.optionText as selectedOptionText " +
        " from ClassroomSurveyAnswer o left outer join o.selectedOption so where o.classroomSurvey.id = :classroomSurveyId")
    List<ClassroomSurveyAnswerListModel> getClassroomSurveyAnswerListModels(@Param("classroomSurveyId") Long classroomSurveyId);

    @Query("select " +
        " so.id as selectedOptionId," +
        " so.optionText as selectedOptionText," +
        " count(o) as selectedOptionCount " +
        " from ClassroomSurveyAnswer o inner join o.selectedOption so where o.classroomSurvey.id = :classroomSurveyId " +
        " group by so.id, so.optionText")
    List<ClassroomSurveyAnswerReportDto> getClassroomSurveyAnswerReportDto(@Param("classroomSurveyId")Long classroomSurveyId);
}
