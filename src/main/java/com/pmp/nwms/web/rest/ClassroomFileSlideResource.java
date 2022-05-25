package com.pmp.nwms.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pmp.nwms.service.ClassroomFileSlideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/classroom/file/slide")
public class ClassroomFileSlideResource {

    @Autowired
    private ClassroomFileSlideService classroomFileSlideService;

    @PostMapping("/create/{classroomFileId}")
    @Timed
    public ResponseEntity<Long> create(@PathVariable("classroomFileId") Long classroomFileId) {
        Long slideCount = classroomFileSlideService.createSlidesOrGetSlidesInfo(classroomFileId);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Server-Date", new Date().toInstant().toString());
        return ResponseEntity.ok().headers(headers).body(slideCount);
    }


    @PostMapping(value = {"/get/{classroomFileId}", "/get/{classroomFileId}/{fromSlide}", "/get/{classroomFileId}/{fromSlide}/{toSlide}"})
    @Timed
    public ResponseEntity<List<byte[]>> get(@PathVariable("classroomFileId") Long classroomFileId, @PathVariable(value = "fromSlide") Optional<Integer> fromSlide, @PathVariable(value = "toSlide") Optional<Integer> toSlide) {
        List<byte[]> result = classroomFileSlideService.getClassroomFileSlides(classroomFileId, fromSlide, toSlide);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Server-Date", new Date().toInstant().toString());
        return ResponseEntity.ok().headers(headers).body(result);
    }

}
