package com.pmp.nwms.scheduler;

import com.pmp.nwms.NwmsConfig;
import com.pmp.nwms.domain.ClassroomRecordingInfo;
import com.pmp.nwms.domain.enums.RecordingStorageStatus;
import com.pmp.nwms.model.FilePathInfoModel;
import com.pmp.nwms.repository.ClassroomRecordingInfoRepository;
import com.pmp.nwms.util.JackrabbitRepoUtil;
import com.pmp.nwms.util.date.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class RemoveRecordingFilesScheduler {
    private final Logger logger = LoggerFactory.getLogger(RemoveRecordingFilesScheduler.class);

    @Autowired
    private NwmsConfig nwmsConfig;
    @Autowired
    private ClassroomRecordingInfoRepository classroomRecordingInfoRepository;
    @Autowired
    private JackrabbitRepoUtil jackrabbitRepoUtil;

    @Scheduled(cron = "${remove.recording.files.scheduler.cron}")
    public void run() {
        if (!nwmsConfig.isRemoveRecordingFilesSchedulerEnabled()) {
            logger.info("RemoveRecordingFilesScheduler is disabled.");
            return;
        }
        Date checkDate = DateUtil.plusDays(new Date(), -1 * nwmsConfig.getKeepRecordingsMaxDayCount());
        logger.info("checkDate is                  : " + checkDate);
        logger.info("nwmsConfig.getRubruWsUrl() is : " + nwmsConfig.getRubruWsUrl());
        List<ClassroomRecordingInfo> classroomRecordingInfos = classroomRecordingInfoRepository.findByDownloadBaseUrlAndStorageStatusDownloadedBeforeDate(nwmsConfig.getRubruWsUrl(), RecordingStorageStatus.Downloaded, checkDate);
        if (classroomRecordingInfos != null && !classroomRecordingInfos.isEmpty()) {
            logger.info("found " + classroomRecordingInfos.size() + " classroomRecordingInfos to delete");
            ArrayList<FilePathInfoModel> files = new ArrayList<>();
            for (ClassroomRecordingInfo classroomRecordingInfo : classroomRecordingInfos) {
                files.add(new FilePathInfoModel() {
                    @Override
                    public String getSubPath() {
                        return classroomRecordingInfo.getSubPath();
                    }

                    @Override
                    public String getName() {
                        return classroomRecordingInfo.getSavedId();
                    }
                });
                classroomRecordingInfo.setStorageStatus(RecordingStorageStatus.Deleted);
            }
            try {
                jackrabbitRepoUtil.removeBatch(files);
            } catch (Exception e) {
                logger.warn(e.getClass().getName() + " - " + e.getMessage());
            }
            try {
                classroomRecordingInfoRepository.saveAll(classroomRecordingInfos);
            } catch (Exception e) {
                logger.warn(e.getClass().getName() + " - " + e.getMessage());
            }
        }else{
            logger.info("no classroomRecordingInfos was found  to delete");
        }
    }
}
