package com.pmp.nwms.service;

import com.pmp.nwms.service.dto.WebinarClassroomStudentDTO;
import com.pmp.nwms.service.dto.WebinarClassroomStudentResultDto;
import com.pmp.nwms.service.listmodel.WebinarClassroomStudentListModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

public interface ClassroomStudentService {
    @Transactional
    WebinarClassroomStudentResultDto saveWebinarClassroomStudent(WebinarClassroomStudentDTO dto);

    @Transactional
    void delete(Long classroomId, Long studentId);

    Page<WebinarClassroomStudentListModel> findWebinarClassroomStudentDTOsByClassroomId(Long classroomId, Pageable pageable);

    @Transactional
    void fixStudentsData();
}
