package com.pmp.nwms.service.impl;

import com.pmp.nwms.domain.ClassroomRecordingInfo;
import com.pmp.nwms.domain.enums.RecordingStorageStatus;
import com.pmp.nwms.model.ClassroomRecordingInfoChecksumModel;
import com.pmp.nwms.repository.ClassroomRecordingInfoRepository;
import com.pmp.nwms.repository.ClassroomRepository;
import com.pmp.nwms.service.ClassroomRecordingInfoService;
import com.pmp.nwms.service.dto.OpenClassroomRecordingInfoDTO;
import com.pmp.nwms.service.listmodel.ClassroomRecordingInfoListModel;
import com.pmp.nwms.util.ClassroomUtil;
import com.pmp.nwms.util.JackrabbitRepoUtil;
import com.pmp.nwms.web.rest.errors.EntityNotFountByIdException;
import com.pmp.nwms.web.rest.errors.InvalidClassroomRecordingInfoChecksumException;
import com.pmp.nwms.web.rest.errors.UnableToDownloadClassroomRecordingDueToRecordingStorageStatusException;
import com.pmp.nwms.web.rest.vm.FileUploadVM;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service("classroomRecordingInfoService")
public class ClassroomRecordingInfoServiceImpl implements ClassroomRecordingInfoService {
    private final Logger log = LoggerFactory.getLogger(ClassroomRecordingInfoServiceImpl.class);

    @Autowired
    private ClassroomRecordingInfoRepository classroomRecordingInfoRepository;
    @Autowired
    private ClassroomRepository classroomRepository;
    @Autowired
    private JackrabbitRepoUtil jackrabbitRepoUtil;

    @Override
    public List<ClassroomRecordingInfoListModel> getClassroomRecordings(Long classroomId, Long rubruSessionId) {
        ClassroomUtil.checkClassroom(classroomId, classroomRepository);
        if (rubruSessionId == null) {
            return classroomRecordingInfoRepository.getClassroomRecordingInfoListModelsByClassroomId(classroomId);
        } else {
            return classroomRecordingInfoRepository.getClassroomRecordingInfoListModelsByClassroomIdAndRubruSessionId(classroomId, rubruSessionId);
        }
    }

    @Override
    public FileUploadVM getFileContent(Long id) {
        FileUploadVM vm = new FileUploadVM();
        Optional<ClassroomRecordingInfo> byId = classroomRecordingInfoRepository.findById(id);
        if (!byId.isPresent()) {
            throw new EntityNotFountByIdException("ClassroomRecordingInfo", id);
        }
        ClassroomRecordingInfo classroomRecordingInfo = byId.get();

        if (!classroomRecordingInfo.getStorageStatus().equals(RecordingStorageStatus.Downloaded)) {
            throw new UnableToDownloadClassroomRecordingDueToRecordingStorageStatusException(classroomRecordingInfo.getStorageStatus());
        }
        byte[] fileInfoContent = jackrabbitRepoUtil.getFileInfoContent(classroomRecordingInfo.getSubPath(), classroomRecordingInfo.getSavedId());
        vm.setContent(fileInfoContent);
        vm.setContentType(classroomRecordingInfo.getContentType());
        vm.setName(classroomRecordingInfo.getRecordingName() + ".mp4");


        return vm;
    }

    @Override
    public OpenClassroomRecordingInfoDTO getOpenClassroomRecordingInfoDTOForClassroom(Long classroomId) {
        List<OpenClassroomRecordingInfoDTO> dtos = classroomRecordingInfoRepository.findOpenClassroomRecordingInfoDTO(classroomId, RubruWebhookServiceImpl.RecordingStatus.started.name());
        if (dtos != null && !dtos.isEmpty()) {
            if (dtos.size() > 1) {
                log.warn("classromm by id " + classroomId + " has " + dtos.size() + " recordings in started state, this means we have some invalid data that need to be cleaned up!");
            }
            return dtos.get(0);
        }
        return null;
    }

    @Override
    public void validateChecksum(Long id, String checksum) {
        Optional<ClassroomRecordingInfoChecksumModel> model = classroomRecordingInfoRepository.getClassroomRecordingInfoChecksumModel(id);
        model.map(recordingInfo -> {
            String data = recordingInfo.toDataString();
            String expectedChecksum = DigestUtils.sha1Hex(data);
            if (!expectedChecksum.equals(checksum)) {
                throw new InvalidClassroomRecordingInfoChecksumException();
            }
            return recordingInfo;
        })
            .orElseThrow(() -> new EntityNotFountByIdException("ClassroomRecordingInfo", id));
    }

    public static void main(String[] args) {
        Long a = 1609082592791L;
        Date d = new Date(a);
        System.out.println("d = " + d);
    }

}
