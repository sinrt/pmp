package com.pmp.nwms.repository;

import com.pmp.nwms.domain.ClassroomRecordingInfo;
import com.pmp.nwms.domain.enums.RecordingStorageStatus;
import com.pmp.nwms.model.ClassroomRecordingInfoChecksumModel;
import com.pmp.nwms.service.dto.OpenClassroomRecordingInfoDTO;
import com.pmp.nwms.service.impl.RubruWebhookServiceImpl;
import com.pmp.nwms.service.listmodel.ClassroomRecordingInfoListModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ClassroomRecordingInfoRepository extends JpaRepository<ClassroomRecordingInfo, Long> {

    @Query("select o from ClassroomRecordingInfo o where o.classroomId = :classroomId and o.recordingId = :recordingId and o.downloadBaseUrl = :downloadBaseUrl and o.status in :statuses order by o.id desc")
    List<ClassroomRecordingInfo> findByClassroomIdAndRecordingIdAndDownloadBaseUrlInStatuses(@Param("classroomId") Long classroomId, @Param("recordingId") String recordingId, @Param("downloadBaseUrl") String downloadBaseUrl, @Param("statuses") List<String> statuses);

    List<ClassroomRecordingInfo> findByDownloadBaseUrlAndStorageStatus(String downloadBaseUrl, RecordingStorageStatus storageStatus);

    @Query("select o from ClassroomRecordingInfo o where o.downloadBaseUrl = :downloadBaseUrl and o.storageStatus = :storageStatus and o.storeDateTime < :checkDate")
    List<ClassroomRecordingInfo> findByDownloadBaseUrlAndStorageStatusDownloadedBeforeDate(@Param("downloadBaseUrl") String downloadBaseUrl, @Param("storageStatus") RecordingStorageStatus storageStatus, @Param("checkDate") Date checkDate);

    @Query("select " +
        " o.id as id, " +
        " o.startTime as startTime, " +
        " o.startTimeStamp as startTimeStamp, " +
        " o.finalTimeStamp as finalTimeStamp, " +
        " o.duration as duration, " +
        " o.fileSize as fileSize, " +
        " o.recordingId as recordingId, " +
        " o.recordingName as recordingName, " +
        " o.rubruSessionId as rubruSessionId, " +
        " o.rubruSessionName as rubruSessionName, " +
        " o.status as status, " +
        " o.storageStatus as storageStatus, " +
        " o.storeDateTime as storeDateTime " +
        " from ClassroomRecordingInfo o where o.classroomId = :classroomId order by o.id desc")
    List<ClassroomRecordingInfoListModel> getClassroomRecordingInfoListModelsByClassroomId(@Param("classroomId") Long classroomId);


    @Query("select " +
        " o.id as id," +
        " o.recordingId as recordingId," +
        " o.recordingName as recordingName," +
        " o.startTimeStamp as startTimeStamp," +
        " o.storageStatus as storageStatus," +
        " o.classroomId as classroomId " +
        " from ClassroomRecordingInfo o where o.classroomId = :classroomId and o.status= :status order by o.id desc")
    List<OpenClassroomRecordingInfoDTO> findOpenClassroomRecordingInfoDTO(@Param("classroomId") Long classroomId, @Param("status") String status);

    @Query("select " +
        " o.id as id, " +
        " o.startTime as startTime, " +
        " o.startTimeStamp as startTimeStamp, " +
        " o.finalTimeStamp as finalTimeStamp, " +
        " o.duration as duration, " +
        " o.fileSize as fileSize, " +
        " o.recordingId as recordingId, " +
        " o.recordingName as recordingName, " +
        " o.rubruSessionId as rubruSessionId, " +
        " o.rubruSessionName as rubruSessionName, " +
        " o.status as status, " +
        " o.storageStatus as storageStatus, " +
        " o.storeDateTime as storeDateTime " +
        " from ClassroomRecordingInfo o where o.classroomId = :classroomId and o.rubruSessionId = :rubruSessionId order by o.id desc")
    List<ClassroomRecordingInfoListModel> getClassroomRecordingInfoListModelsByClassroomIdAndRubruSessionId(@Param("classroomId") Long classroomId, @Param("rubruSessionId") Long rubruSessionId);

    @Query(value = "select count(distinct(classroom_id)) from classroom_recording_info o inner join classroom c on (o.classroom_id = c.id and c.creator_id = :classroomCreatorId) where o.status = :status", nativeQuery = true)
    long countUsingClassroomCreatorIdAndStatus(@Param("classroomCreatorId") Long classroomCreatorId, @Param("status") String status);

    @Query("select o.id as id, " +
        " o.duration as duration, " +
        " o.fileSize as fileSize, " +
        " c.creator.id as classroomCreatorId" +
        " from ClassroomRecordingInfo o inner join Classroom c on o.classroomId = c.id  where o.id = :id")
    Optional<ClassroomRecordingInfoChecksumModel> getClassroomRecordingInfoChecksumModel(@Param("id") Long id);

    @Modifying
    @Query("update ClassroomRecordingInfo o set o.status = :newStatus where o.rubruSessionId in :rubruSessionIds and o.status in :oldStatuses")
    void closeAllOpenClassroomRecordings(@Param("rubruSessionIds") List<Long> rubruSessionIds, @Param("newStatus") String newStatus, @Param("oldStatuses") List<String> oldStatuses);
}
