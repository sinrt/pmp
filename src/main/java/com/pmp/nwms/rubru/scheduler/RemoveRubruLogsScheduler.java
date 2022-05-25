package com.pmp.nwms.rubru.scheduler;

import com.pmp.nwms.NwmsConfig;
import com.pmp.nwms.logging.data.repository.LogRepository;
import com.pmp.nwms.rubru.data.repository.RubruSignalServerLogRepository;
import com.pmp.nwms.scheduler.RemoveLogsScheduler;
import com.pmp.nwms.util.date.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class RemoveRubruLogsScheduler {
    private final Logger logger = LoggerFactory.getLogger(RemoveRubruLogsScheduler.class);

    @Autowired
    private NwmsConfig nwmsConfig;
    @Autowired
    private RubruSignalServerLogRepository rubruSignalServerLogRepository;


    @Scheduled(cron = "${rubru.remove.logs.scheduler.cron}")
    public void run() {
        if (!nwmsConfig.isRubruRemoveLogsSchedulerEnabled()) {
            logger.info("RemoveRubruLogsScheduler is disabled.");
            return;
        }


        long count = rubruSignalServerLogRepository.count();
        System.out.println("count = " + count);

        Date checkDate = DateUtil.plusDays(new Date(), -1 * nwmsConfig.getRubruRemoveLogsSchedulerKeepMaxDayCount());
        logger.info("checkDate is                  : " + checkDate);
        String appWebUrl = nwmsConfig.getAppWebUrl();
        appWebUrl = "https://online.rubru.me";
        logger.info("nwmsConfig.getAppWebUrl() is : " + appWebUrl);
        Long deletedCount = rubruSignalServerLogRepository.deleteByAppUrlAndLogDateBefore(appWebUrl, checkDate);
        logger.info("deleted " + deletedCount + " logs for " + appWebUrl + " and before " + checkDate);
    }

}
