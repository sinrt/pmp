package com.pmp.nwms.service.impl;

import com.pmp.nwms.domain.*;
import com.pmp.nwms.repository.*;
import com.pmp.nwms.security.NwmsUser;
import com.pmp.nwms.service.ClassroomBlockedClientService;
import com.pmp.nwms.service.listmodel.ClassroomBlockedClientListModel;
import com.pmp.nwms.util.ClassroomUtil;
import com.pmp.nwms.util.UserUtil;
import com.pmp.nwms.web.rest.vm.ClassroomBlockClientVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("classroomBlockedClientService")
public class ClassroomBlockedClientServiceImpl implements ClassroomBlockedClientService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ClassroomRepository classroomRepository;
    @Autowired
    private ClassroomStudentRepository classroomStudentRepository;
    @Autowired
    private ClassroomBlockedClientRepository classroomBlockedClientRepository;
    @Autowired
    private RubruSessionParticipantRepository rubruSessionParticipantRepository;

    @Override
    public int blockClients(ClassroomBlockClientVM vm) {
        Classroom classroom = ClassroomUtil.findClassroom(vm.getClassroomId(), classroomRepository, classroomStudentRepository);
        List<RubruSessionParticipant> participants = rubruSessionParticipantRepository.findByClassroomIdAndClientIds(vm.getClassroomId(), vm.getClientIds());
        int count = 0;
        NwmsUser user = UserUtil.getCurrentUser();

        Date now = new Date();
        for (String clientId : vm.getClientIds()) {
            ClassroomBlockedClient client = new ClassroomBlockedClient();
            client.setClassroom(classroom);
            client.setClientId(clientId);
            client.setBlockTime(now);
            client.setBlockerUserId(user.getId());
            RubruSessionParticipant participant = findParticipant(clientId, participants);
            if (participant != null) {
                //todo should I throw an exception here?!
                client.setParticipantName(participant.getParticipantName());
                client.setBlockedUserId(participant.getUserId());
            }
            client = classroomBlockedClientRepository.save(client);
            count++;
        }

        return count;
    }

    @Override
    public int unblockClients(ClassroomBlockClientVM vm) {
        Classroom classroom = ClassroomUtil.findClassroom(vm.getClassroomId(), classroomRepository, classroomStudentRepository);
        classroomBlockedClientRepository.deleteByClassroomIdAndClientIds(vm.getClassroomId(), vm.getClientIds());
        return vm.getClientIds().size();
    }

    @Override
    public List<ClassroomBlockedClientListModel> getClassroomBlockedClientListModels(Long classroomId) {
        return classroomBlockedClientRepository.getClassroomBlockedClientListModels(classroomId);
    }

    private RubruSessionParticipant findParticipant(String clientId, List<RubruSessionParticipant> participants) {
        if (participants != null && !participants.isEmpty()) {
            for (RubruSessionParticipant participant : participants) {
                if (clientId.equalsIgnoreCase(participant.getClientId())) {
                    return participant;
                }
            }
        }
        return null;
    }
}
