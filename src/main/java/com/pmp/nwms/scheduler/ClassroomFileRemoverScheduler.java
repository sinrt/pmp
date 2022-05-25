package com.pmp.nwms.scheduler;

import com.pmp.nwms.NwmsConfig;
import com.pmp.nwms.domain.ClassroomFile;
import com.pmp.nwms.domain.ClassroomFileSlide;
import com.pmp.nwms.domain.enums.VisibilityStatus;
import com.pmp.nwms.model.FilePathInfoModel;
import com.pmp.nwms.repository.ClassroomFileRepository;
import com.pmp.nwms.repository.ClassroomFileSlideRepository;
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
public class ClassroomFileRemoverScheduler {
    private final Logger logger = LoggerFactory.getLogger(ClassroomFileRemoverScheduler.class);
    @Autowired
    private NwmsConfig nwmsConfig;
    @Autowired
    private ClassroomFileRepository classroomFileRepository;
    @Autowired
    private ClassroomFileSlideRepository classroomFileSlideRepository;
    @Autowired
    private JackrabbitRepoUtil jackrabbitRepoUtil;

    @Scheduled(cron = "${classroom.file.remover.scheduler.cron}")
    public void run() {
        if (!nwmsConfig.isClassroomFileRemoverSchedulerEnabled()) {
            logger.info("ClassroomFileRemoverScheduler is disabled.");
            return;
        }
        Date checkDate = DateUtil.plusDays(new Date(), -1 * nwmsConfig.getClassroomFileRemoverSchedulerKeepMaxDayCount());
        logger.info("checkDate is                  : " + checkDate);
        List<ClassroomFile> classroomFiles = classroomFileRepository.findAllUsingCreateDateBeforeAndNotStatus(checkDate.toInstant(), VisibilityStatus.deleted);
        if(classroomFiles != null && !classroomFiles.isEmpty()){
            List<Long> classroomFileIds = new ArrayList<>();
            ArrayList<FilePathInfoModel> files = new ArrayList<>();
            for (ClassroomFile classroomFile : classroomFiles) {
                classroomFileIds.add(classroomFile.getId());
                classroomFile.setStatus(VisibilityStatus.deleted);
                files.add(new FilePathInfoModel() {
                    @Override
                    public String getSubPath() {
                        return classroomFile.getSubPath();
                    }

                    @Override
                    public String getName() {
                        return classroomFile.getSavedId();
                    }
                });
            }
            try {
                jackrabbitRepoUtil.removeBatch(files);
            } catch (Exception e) {
                logger.warn(e.getClass().getName() + " - " + e.getMessage());
            }
            try {
                classroomFileRepository.saveAll(classroomFiles);
            } catch (Exception e) {
                logger.warn(e.getClass().getName() + " - " + e.getMessage());
            }
            List<ClassroomFileSlide> classroomFileSlides = classroomFileSlideRepository.findAllUsingClassroomFileIds(classroomFileIds);
            if(classroomFileSlides != null && !classroomFileSlides.isEmpty()){
                ArrayList<FilePathInfoModel> slideFiles = new ArrayList<>();
                for (ClassroomFileSlide classroomFileSlide : classroomFileSlides) {
                    slideFiles.add(new FilePathInfoModel() {
                        @Override
                        public String getSubPath() {
                            return classroomFileSlide.getSubPath();
                        }

                        @Override
                        public String getName() {
                            return classroomFileSlide.getSavedId();
                        }
                    });
                }
                try {
                    jackrabbitRepoUtil.removeBatch(slideFiles);
                } catch (Exception e) {
                    logger.warn(e.getClass().getName() + " - " + e.getMessage());
                }
            }
        }
    }
}
