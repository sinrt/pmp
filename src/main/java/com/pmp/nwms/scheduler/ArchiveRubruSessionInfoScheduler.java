package com.pmp.nwms.scheduler;

import com.pmp.nwms.NwmsConfig;
import com.pmp.nwms.domain.ArchivedRubruSession;
import com.pmp.nwms.domain.ArchivedRubruSessionParticipant;
import com.pmp.nwms.domain.RubruSession;
import com.pmp.nwms.domain.RubruSessionParticipant;
import com.pmp.nwms.repository.ArchivedRubruSessionParticipantRepository;
import com.pmp.nwms.repository.ArchivedRubruSessionRepository;
import com.pmp.nwms.repository.RubruSessionParticipantRepository;
import com.pmp.nwms.repository.RubruSessionRepository;
import com.pmp.nwms.util.date.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class ArchiveRubruSessionInfoScheduler {
    private final Logger logger = LoggerFactory.getLogger(ArchiveRubruSessionInfoScheduler.class);

    @Autowired
    private NwmsConfig nwmsConfig;
    @Autowired
    private RubruSessionRepository rubruSessionRepository;
    @Autowired
    private RubruSessionParticipantRepository rubruSessionParticipantRepository;
    @Autowired
    private ArchivedRubruSessionRepository archivedRubruSessionRepository;
    @Autowired
    private ArchivedRubruSessionParticipantRepository archivedRubruSessionParticipantRepository;

    @Scheduled(cron = "${archive.rubru.session.info.scheduler.cron}")
    public void run() {
        if (!nwmsConfig.isArchiveRubruSessionInfoSchedulerEnabled()) {
            logger.info("ArchiveRubruSessionInfoScheduler is disabled.");
            return;
        }

        Date now = new Date();
        Date archiveBeforeDate = DateUtil.roundDate(DateUtil.plusMonths(now, -1 * nwmsConfig.getArchiveRubruSessionInfoAfterMonths())).getTime();
        logger.info("going to find rubruSessions created before " + archiveBeforeDate);
        List<RubruSession> rubruSessions = rubruSessionRepository.findAllByStartDateBefore(archiveBeforeDate, PageRequest.of(0, nwmsConfig.getMaxArchiveCountPerRun(), Sort.by(Sort.Direction.ASC, "id")));
        if (rubruSessions != null && !rubruSessions.isEmpty()) {
            logger.info("found " + rubruSessions.size() + " rubruSessions to archive");
            StringBuilder sss = new StringBuilder("\n\n\n-----");
            long t1 = System.currentTimeMillis();
            for (RubruSession rubruSession : rubruSessions) {
//                long t11 = System.currentTimeMillis();
                ArchivedRubruSession archivedRubruSession = new ArchivedRubruSession(rubruSession);
                archivedRubruSession.setArchiveDate(now);
                archivedRubruSession = archivedRubruSessionRepository.save(archivedRubruSession);
                List<RubruSessionParticipant> rubruSessionParticipants = rubruSessionParticipantRepository.getByRubruSessionId(rubruSession.getId());
                if (rubruSessionParticipants != null && !rubruSessionParticipants.isEmpty()) {
                    ArrayList<ArchivedRubruSessionParticipant> archivedRubruSessionParticipants = new ArrayList<>();
                    for (RubruSessionParticipant rubruSessionParticipant : rubruSessionParticipants) {
                        ArchivedRubruSessionParticipant archivedRubruSessionParticipant = new ArchivedRubruSessionParticipant(rubruSessionParticipant);
                        archivedRubruSessionParticipants.add(archivedRubruSessionParticipant);
                    }
                    archivedRubruSessionParticipantRepository.saveAll(archivedRubruSessionParticipants);
                    rubruSessionParticipantRepository.deleteAll(rubruSessionParticipants);
                }
//                long t21 = System.currentTimeMillis();
//                sss.append("t21-t11 : ").append((t21 - t11)).append("\n");
            }
            rubruSessionRepository.deleteAll(rubruSessions);
            long t2 = System.currentTimeMillis();
            sss.append("----\n\ntotal time : ").append((t2 - t1)).append("\n-----\n\n\n");
            logger.info("execution info : " + sss.toString());
        } else {
            logger.info("no rubruSessions found to archive!");
        }

    }


}
