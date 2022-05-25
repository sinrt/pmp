package com.pmp.nwms.service;

import com.pmp.nwms.logging.data.entity.Log;
import com.pmp.nwms.service.listmodel.AuthenticateLogReportListModel;
import com.pmp.nwms.service.so.AuthenticateLogReportSo;
import com.pmp.nwms.service.so.LogSo;
import org.springframework.data.domain.Page;

public interface LogReportService {
    Page<Log> getLogsReport(LogSo so);

    Page<AuthenticateLogReportListModel> getAuthenticateLogsReport(AuthenticateLogReportSo so);
}
