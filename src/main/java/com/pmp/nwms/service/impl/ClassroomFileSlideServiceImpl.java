package com.pmp.nwms.service.impl;

import com.pmp.nwms.domain.ClassroomFile;
import com.pmp.nwms.domain.ClassroomFileSlide;
import com.pmp.nwms.domain.enums.VisibilityStatus;
import com.pmp.nwms.repository.ClassroomFileRepository;
import com.pmp.nwms.repository.ClassroomFileSlideRepository;
import com.pmp.nwms.repository.ClassroomStudentRepository;
import com.pmp.nwms.repository.UserRepository;
import com.pmp.nwms.service.ClassroomFileSlideService;
import com.pmp.nwms.util.ClassroomUtil;
import com.pmp.nwms.util.JackrabbitRepoUtil;
import com.pmp.nwms.util.slidemaker.FileToSlideConverter;
import com.pmp.nwms.util.slidemaker.SlideableTypes;
import com.pmp.nwms.web.rest.errors.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service("classroomFileSlideService")
public class ClassroomFileSlideServiceImpl implements ClassroomFileSlideService {
    private final Logger log = LoggerFactory.getLogger(ClassroomFileSlideServiceImpl.class);

    @Autowired
    private ClassroomFileSlideRepository classroomFileSlideRepository;

    @Autowired
    private ClassroomFileRepository classroomFileRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClassroomStudentRepository classroomStudentRepository;

    @Autowired
    private JackrabbitRepoUtil jackrabbitRepoUtil;

    @Autowired
    private Map<SlideableTypes, FileToSlideConverter> slideConverters;

    private List<Long> inProgressClassroomFileIds = new ArrayList<>();
    private Map<Long, RuntimeException> slidingErrorClassroomFiles = new HashMap<>();

    @Override
    public Long createSlidesOrGetSlidesInfo(Long classroomFileId) {
        if (inProgressClassroomFileIds.contains(classroomFileId)) {
            throw new ClassroomFileSlidingIsInProgressException();
        }
        if (slidingErrorClassroomFiles.containsKey(classroomFileId)) {
            throw slidingErrorClassroomFiles.get(classroomFileId);
        }
        long count = classroomFileSlideRepository.countByClassroomFileId(classroomFileId);
        if (count == 0) {
            ClassroomFile classroomFile = getClassroomFile(classroomFileId);
            SlideableTypes slideableType = SlideableTypes.findByContentType(classroomFile.getContentType());
            if (slideableType == null) {
                throw new UnsupportedFileTypeForSlidingException();
            }
            ClassroomUtil.checkClassroomModerator(classroomFile.getClassroom(), classroomStudentRepository);

            Thread thread = new Thread(() -> doCreateClassroomFileSlides(classroomFile, slideableType));
            thread.start();
            throw new ClassroomFileSlidingStartedException();
        }
        return count;
    }

    private void doCreateClassroomFileSlides(ClassroomFile classroomFile, SlideableTypes slideableType) {
        long t1 = 0;
        long t2 = 0;
        long t3 = 0;
        try {
            inProgressClassroomFileIds.add(classroomFile.getId());
            t1 = System.currentTimeMillis();
            List<byte[]> slideFiles = slideConverters.get(slideableType).convertFileToImages(jackrabbitRepoUtil.getFileInfoContent(classroomFile.getSubPath(), classroomFile.getSavedId()));
            t2 = System.currentTimeMillis();
            AtomicInteger slideNumber = new AtomicInteger();
            for (byte[] slideFile : slideFiles) {
                slideNumber.getAndIncrement();
                ClassroomFileSlide classroomFileSlide = new ClassroomFileSlide();
                classroomFileSlide.setSlideNumber(slideNumber.get());
                String uuid = UUID.randomUUID().toString().replace("-", "");
                classroomFileSlide.setSavedId(uuid);
                classroomFileSlide.setSubPath(classroomFile.getSubPath() + "/slides");
                classroomFileSlide.setClassroomFile(classroomFile);
                classroomFileSlide = classroomFileSlideRepository.save(classroomFileSlide);
                jackrabbitRepoUtil.uploadFileInfo(new ByteArrayInputStream(slideFile), classroomFileSlide.getSavedId(), "image/png", classroomFileSlide.getSubPath());
            }
            t3 = System.currentTimeMillis();
        } catch (RuntimeException e) {
            slidingErrorClassroomFiles.put(classroomFile.getId(), e);
        } catch (Exception e) {
            slidingErrorClassroomFiles.put(classroomFile.getId(), new RuntimeException(e));
        } finally {
            inProgressClassroomFileIds.remove(classroomFile.getId());
            if (t2 == 0L) {
                t2 = System.currentTimeMillis();
            }
            if (t3 == 0L) {
                t3 = System.currentTimeMillis();
            }

            StringBuilder logInfo = new StringBuilder("sliding file : ");
            logInfo.append(classroomFile.getId());
            logInfo.append("\ntotal Sliding Time                = ").append(t3 - t1);
            logInfo.append("\nconverting file to slides time    = ").append(t2 - t1);
            logInfo.append("\nsaveing slides in jackrabbit time = ").append(t3 - t2);
            log.info(logInfo.toString());
        }
    }

    private ClassroomFile getClassroomFile(Long classroomFileId) {
        Optional<ClassroomFile> byId = classroomFileRepository.findById(classroomFileId);
        if (!byId.isPresent() || byId.get().getStatus().equals(VisibilityStatus.deleted)) {
            throw new EntityNotFountByIdException("classroomFile", classroomFileId);
        }
        return byId.get();
    }

    @Override
    public List<byte[]> getClassroomFileSlides(Long classroomFileId, Optional<Integer> fromSlide, Optional<Integer> toSlide) {
        ClassroomFile classroomFile = getClassroomFile(classroomFileId);
        long count = classroomFileSlideRepository.countByClassroomFileId(classroomFileId);
        if (count == 0) {
            throw new ClassroomFileNotSlidedException();
        }
        int fromSlideNo = 1;
        if (fromSlide.isPresent()) {
            fromSlideNo = fromSlide.get();
        }
        int toSlideNo = (int) count;
        if (toSlide.isPresent()) {
            toSlideNo = toSlide.get();
        }

        if (fromSlideNo > toSlideNo) {
            throw new InvalidSlideNumbersPeriodException();
        }
        if (fromSlideNo < 1 || fromSlideNo > count) {
            throw new InvalidSlideNumbersPeriodException();
        }
        if (toSlideNo < 1 || toSlideNo > count) {
            throw new InvalidSlideNumbersPeriodException();
        }

        List<ClassroomFileSlide> slides = classroomFileSlideRepository.findByClassroomFileIdInPeriod(classroomFileId, fromSlideNo, toSlideNo);

        ArrayList<byte[]> result = new ArrayList<>();

        for (ClassroomFileSlide slide : slides) {
            result.add(jackrabbitRepoUtil.getFileInfoContent(slide.getSubPath(), slide.getSavedId()));//todo is this good? or should it be done parallel
        }


        return result;
    }
}
