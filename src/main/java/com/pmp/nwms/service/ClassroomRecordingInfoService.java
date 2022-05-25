package com.pmp.nwms.service;

import com.pmp.nwms.service.dto.OpenClassroomRecordingInfoDTO;
import com.pmp.nwms.service.listmodel.ClassroomRecordingInfoListModel;
import com.pmp.nwms.web.rest.vm.FileUploadVM;

import java.util.List;

public interface ClassroomRecordingInfoService {
    List<ClassroomRecordingInfoListModel> getClassroomRecordings(Long classroomId, Long rubruSessionId);

    FileUploadVM getFileContent(Long id);

    OpenClassroomRecordingInfoDTO getOpenClassroomRecordingInfoDTOForClassroom(Long classroomId);

    void validateChecksum(Long id, String checksum);
}
