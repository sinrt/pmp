package com.pmp.nwms.service.impl;

import com.pmp.nwms.NwmsConfig;
import com.pmp.nwms.domain.Classroom;
import com.pmp.nwms.domain.ClassroomFile;
import com.pmp.nwms.domain.enums.VisibilityStatus;
import com.pmp.nwms.repository.ClassroomFileRepository;
import com.pmp.nwms.repository.ClassroomRepository;
import com.pmp.nwms.repository.ClassroomStudentRepository;
import com.pmp.nwms.repository.UserRepository;
import com.pmp.nwms.service.ClassroomFileService;
import com.pmp.nwms.service.listmodel.ClassroomFileListModel;
import com.pmp.nwms.service.so.ClassroomFileSo;
import com.pmp.nwms.service.so.OrderObject;
import com.pmp.nwms.util.ClassroomUtil;
import com.pmp.nwms.util.JackrabbitRepoUtil;
import com.pmp.nwms.web.rest.errors.*;
import com.pmp.nwms.web.rest.vm.FileUploadVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service("classroomFileService")
public class ClassroomFileServiceImpl implements ClassroomFileService {

    @Autowired
    private NwmsConfig nwmsConfig;
    @Autowired
    private ClassroomRepository classroomRepository;
    @Autowired
    private ClassroomFileRepository classroomFileRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClassroomStudentRepository classroomStudentRepository;

    @Autowired
    private JackrabbitRepoUtil jackrabbitRepoUtil;

    @Override
    @Transactional
    public Long uploadNewFile(FileUploadVM vm) {
        Optional<Classroom> classroomByName = classroomRepository.findClassroomByName(vm.getSessionId());
        if (!classroomByName.isPresent()) {
            throw new ClassroomNotFoundByNameException();
        }
        Classroom classroom = classroomByName.get();

        ClassroomUtil.checkClassroomModerator(classroom, classroomStudentRepository);

        validateFile(vm, classroom);
        String sessionId = vm.getSessionId().replace(" ", "");
        String uuid = UUID.randomUUID().toString().replace("-", "");
        String subPath = "classroom/" + sessionId;
        jackrabbitRepoUtil.uploadFileInfo(new ByteArrayInputStream(vm.getContent()), uuid, vm.getContentType(), "classroom", sessionId);
        ClassroomFile classroomFile = new ClassroomFile();
        classroomFile.setClassroom(classroom);
        classroomFile.setContentType(vm.getContentType());
        classroomFile.setFilename(vm.getName());
        classroomFile.setSavedId(uuid);
        classroomFile.setSubPath(subPath);
        classroomFile.setStatus(VisibilityStatus.hidden);//todo ask about this
        classroomFile = classroomFileRepository.save(classroomFile);
        return classroomFile.getId();
    }

    private void validateFile(FileUploadVM vm, Classroom classroom) {
        double sizeInMB = (vm.getContent().length / 1024d) / 1024d;
        if (sizeInMB > nwmsConfig.getMaxFileSize()) {
            throw new InvalidFileSizeException(sizeInMB, nwmsConfig.getMaxFileSize());
        }
        long count = classroomFileRepository.countByClassroom(classroom.getId());
        if (count > nwmsConfig.getMaxFileCountPerSession()) {
            throw new MaxFileCountExceededException(nwmsConfig.getMaxFileCountPerSession());
        }

    }

    @Override
    @Transactional
    public void deleteFile(Long id) {
        ClassroomFile classroomFile = getClassroomFile(id);
        ClassroomUtil.checkClassroomModerator(classroomFile.getClassroom(), classroomStudentRepository);

        jackrabbitRepoUtil.remove(classroomFile.getSubPath(), classroomFile.getSavedId());
        classroomFileRepository.delete(classroomFile);
    }

    private ClassroomFile getClassroomFile(Long id) {
        Optional<ClassroomFile> byId = classroomFileRepository.findById(id);
        if (!byId.isPresent() || byId.get().getStatus().equals(VisibilityStatus.deleted)) {
            throw new EntityNotFountByIdException("classroomFile", id);
        }
        return byId.get();
    }

    @Override
    public FileUploadVM getFileContent(Long id) {
        ClassroomFile classroomFile = getClassroomFile(id);
        byte[] fileInfoContent = jackrabbitRepoUtil.getFileInfoContent(classroomFile.getSubPath(), classroomFile.getSavedId());
        FileUploadVM vm = new FileUploadVM();
        vm.setContent(fileInfoContent);
        vm.setContentType(classroomFile.getContentType());
        vm.setName(classroomFile.getFilename());
        return vm;
    }

    @Override
    public Page<ClassroomFileListModel> findAll(ClassroomFileSo so) {
        Sort sort;
        if (so.getOrderObjects() != null && !so.getOrderObjects().isEmpty()) {
            List<Sort.Order> orderList = new ArrayList<>();
            for (OrderObject ob : so.getOrderObjects()) {
                orderList.add(new Sort.Order(ob.getSortOrder().equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, ob.getSortField()));
            }
            sort = Sort.by(orderList);
        } else {
            sort = Sort.by(Sort.Direction.DESC, "id");
        }


        return classroomFileRepository.findByClassroomId(so.getClassroomId(), VisibilityStatus.deleted, PageRequest.of(so.getPageNumber(), so.getPageSize(), sort));
    }

    @Override
    @Transactional
    public void changeStatus(Long id, VisibilityStatus newStatus) {
        ClassroomFile classroomFile = getClassroomFile(id);
        if (classroomFile.getStatus().equals(newStatus)) {
            throw new ClassroomFileAlreadyInStatusException(id, newStatus);
        }

        ClassroomUtil.checkClassroomModerator(classroomFile.getClassroom(), classroomStudentRepository);

        classroomFile.setStatus(newStatus);
        classroomFile = classroomFileRepository.save(classroomFile);
    }
}
