package com.pmp.nwms.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pmp.nwms.logging.IgnoreLoggingValue;
import com.pmp.nwms.service.ClassroomRecordingInfoService;
import com.pmp.nwms.service.dto.OpenClassroomRecordingInfoDTO;
import com.pmp.nwms.service.listmodel.ClassroomRecordingInfoListModel;
import com.pmp.nwms.util.MultipartFileSender;
import com.pmp.nwms.web.rest.vm.FileUploadVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ClassroomRecordingInfoResource {
    private final Logger log = LoggerFactory.getLogger(ClassroomRecordingInfoResource.class);

    @Autowired
    private ClassroomRecordingInfoService classroomRecordingInfoService;

    @GetMapping(value = {"/classroom/recordings/list/{classroomId}", "/classroom/recordings/list/{classroomId}/{rubruSessionId}"})
    @Timed
    public ResponseEntity<List<ClassroomRecordingInfoListModel>> getClassroomRecordings(@PathVariable("classroomId") Long classroomId, @PathVariable(value = "rubruSessionId", required = false) Long rubruSessionId) {
        List<ClassroomRecordingInfoListModel> models = classroomRecordingInfoService.getClassroomRecordings(classroomId, rubruSessionId);
        return new ResponseEntity<>(models, HttpStatus.OK);
    }

    @GetMapping(value = "/classroom/recording/get/{id}/{checksum}")
    @IgnoreLoggingValue
    public void download(@PathVariable("id") Long id, @PathVariable("checksum") String checksum, HttpServletRequest request, HttpServletResponse response) {
        classroomRecordingInfoService.validateChecksum(id, checksum);
        FileUploadVM vm;
        if (VIDEO_FILES.containsKey(id)) {
            vm = VIDEO_FILES.get(id);
        } else {
            vm = classroomRecordingInfoService.getFileContent(id);
            VIDEO_FILES.put(id, vm);
        }

        try {
            MultipartFileSender.fromVM(vm)
                .with(request)
                .with(response)
                .serveResource();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @GetMapping(value = "/classroom/recording/get/current/info/{classroomId}")
    @Timed
    public ResponseEntity<OpenClassroomRecordingInfoDTO> getClassroomCurrentRecordingInfo(@PathVariable("classroomId") Long classroomId) {
        OpenClassroomRecordingInfoDTO dto = classroomRecordingInfoService.getOpenClassroomRecordingInfoDTOForClassroom(classroomId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }


    private static final Map<Long, FileUploadVM> VIDEO_FILES = new HashMap<>();


    @Scheduled(cron = "0 0 1 * * ?")
    public void cleanupVideosMap() {
        VIDEO_FILES.clear();
    }


}
