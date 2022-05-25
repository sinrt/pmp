package com.pmp.nwms.service.impl;

import com.pmp.nwms.NwmsConfig;
import com.pmp.nwms.domain.PersistentAuditEvent;
import com.pmp.nwms.repository.PersistenceAuditEventRepository;
import com.pmp.nwms.service.PersistentAuditEventService;
import com.pmp.nwms.util.date.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PersistentAuditEventServiceImpl implements PersistentAuditEventService {
    @Autowired
    private PersistenceAuditEventRepository persistenceAuditEventRepository;
    @Autowired
    private NwmsConfig nwmsConfig;

    @Override
    public boolean isCaptchaRequired(String username) {
        Date checkDate = DateUtil.plusMinutes(new Date(), -nwmsConfig.getCaptchaRequiredFailedLoginValidMinutes());
        List<PersistentAuditEvent> events = persistenceAuditEventRepository.getByPrincipalAndAfterAuditEventDate(username, checkDate.toInstant(), PageRequest.of(0, nwmsConfig.getCaptchaRequiredFailedLoginCount()));
        if(events == null || events.isEmpty()){
            return false;
        }else if(events.size() < nwmsConfig.getCaptchaRequiredFailedLoginCount()){
            return false;
        }else{
            for (PersistentAuditEvent event : events) {
                if(event.getAuditEventType().equalsIgnoreCase("AUTHENTICATION_SUCCESS")){
                    return false;
                }
            }
            return true;
        }
    }
}
