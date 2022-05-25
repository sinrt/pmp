package com.pmp.nwms.service;

import com.pmp.nwms.domain.Classroom;
import com.pmp.nwms.domain.RubruSession;
import com.pmp.nwms.service.dto.ClassroomDTO;
import com.pmp.nwms.service.dto.ClassroomStatusDTO;
import com.pmp.nwms.service.dto.WebinarClassroomDTO;
import com.pmp.nwms.service.dto.WebinarClassroomResultDto;
import com.pmp.nwms.service.listmodel.ClassroomListModel;
import com.pmp.nwms.service.listmodel.RubruSessionListModel;
import com.pmp.nwms.service.listmodel.UserClassroomDataListModel;
import com.pmp.nwms.service.listmodel.UserClassroomListModel;
import com.pmp.nwms.web.rest.vm.ClassroomSettingsVM;
import com.pmp.nwms.web.rest.vm.ClassroomStudentFileDownloadVM;
import com.pmp.nwms.web.rest.vm.DynamicStudentDataVM;
import com.pmp.nwms.web.rest.vm.RubruSessionParticipantsReportVM;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Classroom.
 */
public interface ClassroomService {

    /**
     * Save a classroom.
     *
     * @param classroom the entity to updateClassroom
     * @return the persisted entity
     */
    ClassroomDTO save(ClassroomDTO classroom);

    /**
     * Get all the classrooms.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Classroom> findAll(Pageable pageable);

    Optional<ClassroomDTO> updateClassroom(ClassroomDTO classroomDTO);

    /**
     * Get all the Classroom with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    Page<Classroom> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" classroom.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Classroom> findOne(Long id);

    List<ClassroomDTO> findUserClasses(Long id);

    Classroom findClassroomByName(String name);

    Optional<Classroom> findClassroomByUUID(String uuid);

    /**
     * Delete the "id" classroom.
     *
     * @param id the id of the entity
     */

    @Transactional
    void delete(Long id);

    void deleteByName(String name);

    Classroom findClassByUserIdAndDate(Long userId);

    List<Classroom> findMasterClassrooms(Long id);

    List<ClassroomListModel> findByCreatorAndGStatus(Long userId, boolean isGuestRoom);

    @Transactional
    WebinarClassroomResultDto saveWebinarClassroom(WebinarClassroomDTO dto);

    WebinarClassroomDTO getWebinarClassroomDTO(Long classroomId);

    Page<WebinarClassroomDTO> findWebinarClassroomDTOsByCourseId(Long courseId, Pageable pageable);

    ClassroomStatusDTO getClassroomStatus(String clientId, String name, String userToken);

    ClassroomStatusDTO getClassroomStatus(String clientId, String name, DynamicStudentDataVM vm);

    @Transactional
    List<Long> uploadClassroomStudentsFile(Long courseId, Long classroomId, String fileName, String fileContentType, InputStream fileInputStream);

    ClassroomStudentFileDownloadVM getClassroomStudentFileDownloadVM(Long classroomId);

    List<RubruSessionListModel> getClassroomSessionsReportData(Long classroomId);

    List<RubruSessionListModel> getClassroomSessionsReportData(String sessionName);

    ClassroomStudentFileDownloadVM getRubruSessionParticipantsReportVM(Long rubruSessionId);

    @Transactional
    int updateClassroomSettings(ClassroomSettingsVM vm);

    List<UserClassroomDataListModel> findUserClassroomListModels(boolean active);

    long countClassroomByName(String name);

    List<UserClassroomDataListModel> getCreatorClassrooms(boolean isTeacherPan);

    @Transactional
    Long createNewClassroomDto(ClassroomDTO classroomDTO);

    @Transactional
    void fullDeleteClassroom(Long classroomId);

    List<RubruSessionParticipantsReportVM> getClassroomSessionParticipantsReport(Long rubruSessionId);
}
