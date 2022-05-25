package com.pmp.nwms.util;

import com.pmp.nwms.model.OpenParticipantModel;
import com.pmp.nwms.repository.RubruSessionParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WebhookDataFixerScheduler {
    @Autowired
    private RubruSessionParticipantRepository rubruSessionParticipantRepository;

    @Scheduled(cron = "${close.open.participants.after.session.closed.scheduler.cron}")
    public void closeOpenParticipantsAfterSessionIsClosed() {

        List<OpenParticipantModel> participants = rubruSessionParticipantRepository.findOpenParticipantsWithClosedSessions();
        if(participants != null && !participants.isEmpty()){
            for (OpenParticipantModel model : participants) {
                long duration = (model.getSessionEndDate().getTime() - model.getParticipantJoinDateTime().getTime()) / 1000L;
                rubruSessionParticipantRepository.closeParticipant(model.getRubruSessionParticipantId(), duration, model.getSessionEndDate(), "forceCloseUnclosedByScheduler");
            }
        }
    }
}
