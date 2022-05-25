package com.pmp.nwms.service;

import com.pmp.nwms.service.listmodel.ClassroomBlockedClientListModel;
import com.pmp.nwms.web.rest.vm.ClassroomBlockClientVM;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ClassroomBlockedClientService {
    @Transactional
    int blockClients(ClassroomBlockClientVM vm);

    @Transactional
    int unblockClients(ClassroomBlockClientVM vm);

    List<ClassroomBlockedClientListModel> getClassroomBlockedClientListModels(Long classroomId);
}
