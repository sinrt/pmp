package com.pmp.nwms.repository;

import com.pmp.nwms.domain.ClassroomFile;
import com.pmp.nwms.domain.enums.VisibilityStatus;
import com.pmp.nwms.model.FilePathInfoModel;
import com.pmp.nwms.service.listmodel.ClassroomFileListModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Repository
public interface ClassroomFileRepository extends JpaRepository<ClassroomFile, Long> {
    @Query("select count(o) from ClassroomFile o where o.classroom.id = :classroomId")
    long countByClassroom(@Param("classroomId") Long classroomId);

    @Query("select o.id as id, " +
        "o.filename as filename, " +
        "o.contentType as contentType, " +
        "o.status as status" +
        " from ClassroomFile o where o.classroom.id = :classroomId and o.status <> :status")
    Page<ClassroomFileListModel> findByClassroomId(@Param("classroomId") Long classroomId,
                                                   @Param("status") VisibilityStatus status,
                                                   Pageable pageable);

    @Query("select o.subPath as subPath, o.savedId as name from ClassroomFile o where o.classroom.id = :classroomId")
    List<FilePathInfoModel> findAllFilePathInfoModelsUsingClassroomId(@Param("classroomId") Long classroomId);

    @Modifying
    @Query("delete from ClassroomFile o where o.classroom.id = :classroomId")
    void deleteAllUsingClassroomId(@Param("classroomId") Long classroomId);

    @Query("select o.subPath as subPath, o.savedId as name from ClassroomFile o inner join o.classroom c where c.course.id = :courseId")
    List<FilePathInfoModel> findAllFilePathInfoModelsUsingCourseId(@Param("courseId") Long courseId);

    @Modifying
    @Query("delete from ClassroomFile o where o.classroom.id in (select c.id from Classroom c where c.course.id = :courseId)")
    void deleteAllUsingCourseId(@Param("courseId") Long courseId);

    @Query("select o from ClassroomFile o where o.createdDate < :checkDate and o.status <> :status")
    List<ClassroomFile> findAllUsingCreateDateBeforeAndNotStatus(@Param("checkDate") Instant checkDate, @Param("status") VisibilityStatus status);
}
