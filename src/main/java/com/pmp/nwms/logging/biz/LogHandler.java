package com.pmp.nwms.logging.biz;

import com.pmp.nwms.logging.data.entity.Log;

public interface LogHandler {
    void addLogToQueue(Log log);
    void saveLogs();
}
