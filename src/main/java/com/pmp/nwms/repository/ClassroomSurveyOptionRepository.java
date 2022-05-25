package com.pmp.nwms.repository;

import com.pmp.nwms.domain.ClassroomSurveyOption;
import com.pmp.nwms.service.dto.ClassroomSurveyOptionDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassroomSurveyOptionRepository extends JpaRepository<ClassroomSurveyOption, Long> {
    @Modifying
    @Query("delete from ClassroomSurveyOption o where o.classroomSurvey.id = :classroomSurveyId")
    void deleteByClassroomSurveyId(@Param("classroomSurveyId") Long classroomSurveyId);

    @Query("select " +
        " o.id as id, " +
        " o.classroomSurvey.id as classroomSurveyId, " +
        " o.optionText as optionText, " +
        " o.optionOrder as optionOrder " +
        " from ClassroomSurveyOption o where o.classroomSurvey.id = :classroomSurveyId order by o.optionOrder asc")
    List<ClassroomSurveyOptionDTO> findClassroomSurveyOptionDTOsUsingClassroomSurveyId(@Param("classroomSurveyId")Long classroomSurveyId);


    @Modifying
    @Query("delete from ClassroomSurveyOption o where o.classroomSurvey.id = :classroomSurveyId and o.id not in :ids")
    void deleteByClassroomSurveyIdAndNotIds(@Param("classroomSurveyId")Long classroomSurveyId, @Param("ids") List<Long> ids);

    @Query("select " +
        " o.id as id, " +
        " o.classroomSurvey.id as classroomSurveyId, " +
        " o.optionText as optionText, " +
        " o.optionOrder as optionOrder " +
        " from ClassroomSurveyOption o where o.classroomSurvey.id in :classroomSurveyIds order by o.optionOrder asc")
    List<ClassroomSurveyOptionDTO> findClassroomSurveyOptionDTOsUsingClassroomSurveyIds(@Param("classroomSurveyIds") List<Long> classroomSurveyIds);

    long countByIdAndClassroomSurveyId(Long id, Long classroomSurveyId);
}
