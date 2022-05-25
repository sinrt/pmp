package com.pmp.nwms.web.rest;


import com.codahale.metrics.annotation.Timed;
import com.pmp.nwms.logging.data.entity.Log;
import com.pmp.nwms.security.AuthoritiesConstants;
import com.pmp.nwms.service.LogReportService;
import com.pmp.nwms.service.listmodel.AuthenticateLogReportListModel;
import com.pmp.nwms.service.so.AuthenticateLogReportSo;
import com.pmp.nwms.service.so.LogSo;
import com.pmp.nwms.web.rest.util.PaginationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class LogReportResource {

    @Autowired
    private LogReportService logReportService;

    @PostMapping("/log/report")
    @Timed
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<List<Log>> getLogsReport(@Valid @RequestBody LogSo so) {
        Page<Log> page = logReportService.getLogsReport(so);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/log/report");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @PostMapping("/log/authenticate/report")
    @Timed
    public ResponseEntity<List<AuthenticateLogReportListModel>> getAuthenticateLogsReport(@Valid @RequestBody AuthenticateLogReportSo so) {
        Page<AuthenticateLogReportListModel> page = logReportService.getAuthenticateLogsReport(so);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/log/authenticate/report");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }



}
