package com.pmp.nwms.scheduler;

import com.pmp.nwms.NwmsConfig;
import com.pmp.nwms.logging.data.repository.LogRepository;
import com.pmp.nwms.util.date.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class RemoveLogsScheduler {
    private final Logger logger = LoggerFactory.getLogger(RemoveLogsScheduler.class);

    @Autowired
    private NwmsConfig nwmsConfig;
    @Autowired
    private LogRepository logRepository;


    @Scheduled(cron = "${remove.logs.scheduler.cron}")
    public void run() {
        if (!nwmsConfig.isRemoveLogsSchedulerEnabled()) {
            logger.info("RemoveLogsScheduler is disabled.");
            return;
        }
        Date checkDate = DateUtil.plusDays(new Date(), -1 * nwmsConfig.getRemoveLogsSchedulerKeepMaxDayCount());
        logger.info("checkDate is                  : " + checkDate);
        logger.info("nwmsConfig.getAppWebUrl() is : " + nwmsConfig.getAppWebUrl());
        Long deletedCount = logRepository.deleteByAppWebUrlAndCallDateTimeBefore(nwmsConfig.getAppWebUrl(), checkDate);
        logger.info("deleted " + deletedCount + " logs for " + nwmsConfig.getAppWebUrl() + " and before " + checkDate);
    }
}
