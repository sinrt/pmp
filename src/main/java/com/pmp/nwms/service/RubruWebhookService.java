package com.pmp.nwms.service;

import com.pmp.nwms.web.rest.vm.rubru.RubruEventVM;
import org.springframework.transaction.annotation.Transactional;

public interface RubruWebhookService {

    @Transactional
    void saveNewEvent(RubruEventVM rubruEventVM);
}
