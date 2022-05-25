package com.pmp.nwms.service;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ClassroomFileSlideService {
    @Transactional
    Long createSlidesOrGetSlidesInfo(Long classroomFileId);

    List<byte[]> getClassroomFileSlides(Long classroomFileId, Optional<Integer> fromSlide, Optional<Integer> toSlide);
}
