package com.pmp.nwms.service.util;

import com.pmp.nwms.domain.PurchaseStatus;
import com.pmp.nwms.repository.ClassroomRepository;
import com.pmp.nwms.repository.PurchaseStatusRepository;
import com.pmp.nwms.service.dto.ClassroomCheckStatusDTO;
import com.pmp.nwms.web.rest.errors.ClassroomNotFoundByNameException;
import com.pmp.nwms.web.rest.errors.UserHasNoActivePlanException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

@Component("classroomStatusDataHolder")
public class ClassroomStatusDataHolder {
    private Map<String, ClassroomCheckStatusDTO> classrooms = new HashMap<>();
    private Map<Long, PurchaseStatus> usersCurrentPlan = new HashMap<>();

    @Autowired
    private ClassroomRepository classroomRepository;

    @Autowired
    private PurchaseStatusRepository purchaseStatusRepository;

    public ClassroomCheckStatusDTO findByUuid(String uuid) {
        ClassroomCheckStatusDTO dto = classrooms.get(uuid);
        if (dto == null) {
            Optional<ClassroomCheckStatusDTO> op = classroomRepository.findClassroomCheckStatusDTOUsingUUID(uuid);
            if (op.isPresent()) {
                dto = op.get();
                classrooms.put(uuid, dto);
            } else {
                throw new ClassroomNotFoundByNameException();
            }
        }
        return dto;
    }

    public PurchaseStatus findPurchaseStatusByUser(Long userId) {
        PurchaseStatus purchaseStatus = usersCurrentPlan.get(userId);
        if (purchaseStatus == null) {
            Optional<PurchaseStatus> currentPlan = purchaseStatusRepository.findCurrentPlan(userId);
            if (currentPlan.isPresent()) {
                purchaseStatus = currentPlan.get();
            } else {
                purchaseStatus = new PurchaseStatus();
                purchaseStatus.setId(-1L);
            }
            usersCurrentPlan.put(userId, purchaseStatus);
        }

        if (purchaseStatus.getId().equals(-1L)) {
            throw new UserHasNoActivePlanException();
        }

        return purchaseStatus;
    }

    public void removeClassroom(String name) {
        classrooms.remove(name);
    }

    public void removeUserPurchaseStatus(Long userId) {
        usersCurrentPlan.remove(userId);
    }

    @Scheduled(cron = "${classroom.status.data.holder.cleanup.cron}")
    public void cleanup() {
        classrooms = new HashMap<>();
        usersCurrentPlan = new HashMap<>();
    }

    public void removeClassroomById(Long classroomId) {
        List<String> names = new ArrayList<>();
        for (Map.Entry<String, ClassroomCheckStatusDTO> entry : classrooms.entrySet()) {
            if (entry.getValue().getId().equals(classroomId)) {
                names.add(entry.getKey());
            }
        }
        if (!names.isEmpty()) {
            for (String name : names) {
                removeClassroom(name);
            }
        }
    }
}
