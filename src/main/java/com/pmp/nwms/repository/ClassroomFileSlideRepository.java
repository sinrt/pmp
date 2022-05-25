package com.pmp.nwms.repository;

import com.pmp.nwms.domain.ClassroomFileSlide;
import com.pmp.nwms.model.FilePathInfoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassroomFileSlideRepository extends JpaRepository<ClassroomFileSlide, Long> {
    long countByClassroomFileId(@Param("classroomFileId") Long classroomFileId);

    @Query("select o from ClassroomFileSlide o where o.classroomFile.id = :classroomFileId and o.slideNumber >= :fromSlide and o.slideNumber <= :toSlide")
    List<ClassroomFileSlide> findByClassroomFileIdInPeriod(@Param("classroomFileId")Long classroomFileId, @Param("fromSlide")int fromSlide, @Param("toSlide")int toSlide);

    @Query("select o.subPath as subPath, o.savedId as name from ClassroomFileSlide o inner join o.classroomFile cf where cf.classroom.id = :classroomId")
    List<FilePathInfoModel> findAllFilePathInfoModelsUsingClassroomId(@Param("classroomId")Long classroomId);

    @Modifying
    @Query("delete from ClassroomFileSlide o where o.classroomFile.id in (select cf.id from ClassroomFile cf where cf.classroom.id = :classroomId)")
    void deleteAllUsingClassroomId(@Param("classroomId")Long classroomId);

    @Query("select o.subPath as subPath, o.savedId as name from ClassroomFileSlide o inner join o.classroomFile cf inner join cf.classroom c where c.course.id = :courseId")
    List<FilePathInfoModel> findAllFilePathInfoModelsUsingCourseId(@Param("courseId") Long courseId);

    @Modifying
    @Query("delete from ClassroomFileSlide o where o.classroomFile.id in (select cf.id from ClassroomFile cf inner join cf.classroom c where c.course.id = :courseId)")
    void deleteAllUsingCourseId(@Param("courseId") Long courseId);

    @Query("select o from ClassroomFileSlide o where o.classroomFile.id in :classroomFileIds")
    List<ClassroomFileSlide> findAllUsingClassroomFileIds(@Param("classroomFileIds") List<Long> classroomFileIds);
}
