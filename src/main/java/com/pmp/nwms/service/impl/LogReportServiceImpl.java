package com.pmp.nwms.service.impl;

import com.pmp.nwms.logging.data.entity.Log;
import com.pmp.nwms.logging.data.entity.QLog;
import com.pmp.nwms.logging.data.repository.LogRepository;
import com.pmp.nwms.repository.UserRepository;
import com.pmp.nwms.service.LogReportService;
import com.pmp.nwms.service.enums.AuthenticateLogReportMode;
import com.pmp.nwms.service.listmodel.AuthenticateLogReportListModel;
import com.pmp.nwms.service.so.AuthenticateLogReportSo;
import com.pmp.nwms.service.so.LogSo;
import com.pmp.nwms.service.so.OrderObject;
import com.pmp.nwms.util.UserUtil;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LogReportServiceImpl implements LogReportService {
    @Autowired
    private LogRepository logRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public Page<Log> getLogsReport(LogSo so) {
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

        QLog qLog = new QLog("nwms-logs");
        BooleanExpression expression = qLog.id.isNotNull();
        if (!so.isClean()) {
            if (so.getFromCallDateTime() != null) {
                expression = expression.and(qLog.callDateTime.goe(so.getFromCallDateTime()));
            }
            if (so.getToCallDateTime() != null) {
                expression = expression.and(qLog.callDateTime.loe(so.getToCallDateTime()));
            }
            if (so.getUserName() != null && !so.getUserName().isEmpty()) {
                expression = expression.and(qLog.userName.equalsIgnoreCase(so.getUserName()));
            }
            if (so.getIpAddress() != null && !so.getIpAddress().isEmpty()) {
                expression = expression.and(qLog.ipAddress.eq(so.getIpAddress()));
            }
            if (so.getLogUri() != null && !so.getLogUri().isEmpty()) {
                expression = expression.and(qLog.logUri.eq(so.getLogUri()));
            }
            if (so.getSuccess() != null) {
                expression = expression.and(qLog.success.eq(so.getSuccess()));
            }
        }


        Page<Log> all = logRepository.findAll(expression, PageRequest.of(so.getPageNumber(), so.getPageSize(), sort));
        return all;
    }

    @Override
    public Page<AuthenticateLogReportListModel> getAuthenticateLogsReport(AuthenticateLogReportSo so) {
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

        QLog qLog = new QLog("nwms-logs");
        BooleanExpression expression = qLog.logUri.eq("/api/authenticate");
        if (!so.isClean()) {
            if (so.getFromCallDateTime() != null) {
                expression = expression.and(qLog.callDateTime.goe(so.getFromCallDateTime()));
            }
            if (so.getToCallDateTime() != null) {
                expression = expression.and(qLog.callDateTime.loe(so.getToCallDateTime()));
            }
            if (so.getMode() != null) {
                if (so.getMode().equals(AuthenticateLogReportMode.CurrentUser)) {
                    expression = expression.and(qLog.userName.equalsIgnoreCase(UserUtil.getCurrentUser().getUsername()));
                } else if (so.getMode().equals(AuthenticateLogReportMode.CreatedByCurrentUser)) {
                    List<String> userNames = userRepository.findAllLoginsByCreatedBy(UserUtil.getCurrentUser().getUsername());
                    expression = expression.and(qLog.userName.in(userNames));
                }
            }
            if (so.getSuccess() != null) {
                expression = expression.and(qLog.success.eq(so.getSuccess()));
            }
        }
        Page<Log> logs = logRepository.findAll(expression, PageRequest.of(so.getPageNumber(), so.getPageSize(), sort));

        List<AuthenticateLogReportListModel> models = new ArrayList<>();
        if (logs.getSize() > 0) {
            for (Log log : logs) {
                models.add(new AuthenticateLogReportListModel(log.getCallDateTime(), log.getUserName(), log.getIpAddress(), log.isSuccess()));
            }
        }
        return new PageImpl<>(models, logs.getPageable(), logs.getTotalElements());
    }
}
