package com.pmp.nwms.service;

import com.pmp.nwms.domain.enums.VisibilityStatus;
import com.pmp.nwms.service.listmodel.ClassroomFileListModel;
import com.pmp.nwms.service.so.ClassroomFileSo;
import com.pmp.nwms.web.rest.vm.FileUploadVM;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

public interface ClassroomFileService {
    @Transactional
    Long uploadNewFile(FileUploadVM vm);

    @Transactional
    void deleteFile(Long id);

    FileUploadVM getFileContent(Long id);

    Page<ClassroomFileListModel> findAll(ClassroomFileSo so);

    @Transactional
    void changeStatus(Long id, VisibilityStatus newStatus);
}
