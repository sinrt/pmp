package com.pmp.nwms.domain;

import com.pmp.nwms.domain.enums.RecordingStorageStatus;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "classroom_recording_info",
indexes = {
    @Index(name = "idx_cri_classroom_status", columnList = "classroom_id, status")
}
)
public class ClassroomRecordingInfo implements Serializable, BaseEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "rubru_session_id", nullable = true)
    private Long rubruSessionId;
    @Column(name = "rubru_session_name", nullable = false, length = 200)
    private String rubruSessionName;
    @Column(name = "recording_id", nullable = false, length = 200)
    private String recordingId;
    @Column(name = "recording_name", nullable = false, length = 200)
    private String recordingName;
    @Column(name = "output_mode", nullable = false, length = 20)
    private String outputMode;
    @Column(name = "resolution", nullable = true, length = 20)
    private String resolution;
    @Column(name = "recording_layout", nullable = true, length = 50)
    private String recordingLayout;
    @Column(name = "custom_layout", nullable = true, length = 200)
    private String customLayout;
    @Column(name = "has_audio", nullable = false)
    private Boolean hasAudio;
    @Column(name = "has_video", nullable = false)
    private Boolean hasVideo;
    @Column(name = "file_size", nullable = false)
    private Long fileSize;
    @Column(name = "status", nullable = true, length = 10)
    private String status;
    @Column(name = "duration", nullable = false)
    private Double duration;
    @Column(name = "reason", nullable = true, length = 50)
    private String reason;
    @Column(name = "start_time", nullable = false)
    private Long startTime;
    @Column(name = "start_time_stamp", nullable = true)
    private Long startTimeStamp;
    @Column(name = "stop_time_stamp", nullable = true)
    private Long stopTimeStamp;
    @Column(name = "final_time_stamp", nullable = true)
    private Long finalTimeStamp;
    @Column(name = "download_base_url", nullable = true, length = 300)
    private String downloadBaseUrl;

    //file storage info
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "storage_status")
    private RecordingStorageStatus storageStatus;
    @Column(name = "store_date_time", nullable = true)
    private Date storeDateTime;
    @Column(name = "content_type", nullable = true, length = 150)
    private String contentType;
    @Column(name = "saved_id", nullable = true, length = 50)
    private String savedId;
    @Column(name = "sub_path", nullable = true, length = 500)
    private String subPath;

    @Column(name = "classroom_id", nullable = false)
    private Long classroomId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRubruSessionId() {
        return rubruSessionId;
    }

    public void setRubruSessionId(Long rubruSessionId) {
        this.rubruSessionId = rubruSessionId;
    }

    public String getRubruSessionName() {
        return rubruSessionName;
    }

    public void setRubruSessionName(String rubruSessionName) {
        this.rubruSessionName = rubruSessionName;
    }

    public String getRecordingId() {
        return recordingId;
    }

    public void setRecordingId(String recordingId) {
        this.recordingId = recordingId;
    }

    public String getRecordingName() {
        return recordingName;
    }

    public void setRecordingName(String recordingName) {
        this.recordingName = recordingName;
    }

    public String getOutputMode() {
        return outputMode;
    }

    public void setOutputMode(String outputMode) {
        this.outputMode = outputMode;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public String getRecordingLayout() {
        return recordingLayout;
    }

    public void setRecordingLayout(String recordingLayout) {
        this.recordingLayout = recordingLayout;
    }

    public String getCustomLayout() {
        return customLayout;
    }

    public void setCustomLayout(String customLayout) {
        this.customLayout = customLayout;
    }

    public Boolean getHasAudio() {
        return hasAudio;
    }

    public void setHasAudio(Boolean hasAudio) {
        this.hasAudio = hasAudio;
    }

    public Boolean getHasVideo() {
        return hasVideo;
    }

    public void setHasVideo(Boolean hasVideo) {
        this.hasVideo = hasVideo;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getDuration() {
        return duration;
    }

    public void setDuration(Double duration) {
        this.duration = duration;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getStartTimeStamp() {
        return startTimeStamp;
    }

    public void setStartTimeStamp(Long startTimeStamp) {
        this.startTimeStamp = startTimeStamp;
    }

    public Long getStopTimeStamp() {
        return stopTimeStamp;
    }

    public void setStopTimeStamp(Long stopTimeStamp) {
        this.stopTimeStamp = stopTimeStamp;
    }

    public Long getFinalTimeStamp() {
        return finalTimeStamp;
    }

    public void setFinalTimeStamp(Long finalTimeStamp) {
        this.finalTimeStamp = finalTimeStamp;
    }

    public String getDownloadBaseUrl() {
        return downloadBaseUrl;
    }

    public void setDownloadBaseUrl(String downloadBaseUrl) {
        this.downloadBaseUrl = downloadBaseUrl;
    }

    public RecordingStorageStatus getStorageStatus() {
        return storageStatus;
    }

    public void setStorageStatus(RecordingStorageStatus storageStatus) {
        this.storageStatus = storageStatus;
    }

    public Date getStoreDateTime() {
        return storeDateTime;
    }

    public void setStoreDateTime(Date storeDateTime) {
        this.storeDateTime = storeDateTime;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getSavedId() {
        return savedId;
    }

    public void setSavedId(String savedId) {
        this.savedId = savedId;
    }

    public String getSubPath() {
        return subPath;
    }

    public void setSubPath(String subPath) {
        this.subPath = subPath;
    }

    public Long getClassroomId() {
        return classroomId;
    }

    public void setClassroomId(Long classroomId) {
        this.classroomId = classroomId;
    }

    @Override
    public Long fetchPrimaryKey() {
        return getId();
    }
}
