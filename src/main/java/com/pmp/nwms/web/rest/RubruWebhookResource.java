package com.pmp.nwms.web.rest;

import com.pmp.nwms.service.RubruWebhookService;
import com.pmp.nwms.web.rest.vm.rubru.RubruEventVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rubru")
public class RubruWebhookResource {

    private final Logger log = LoggerFactory.getLogger(RubruWebhookResource.class);

    @Autowired
    private RubruWebhookService rubruWebhookService;

    @PostMapping("/webhook")
    public ResponseEntity<Void> logWebhookInfo(@RequestBody RubruEventVM rubruEventVM){
        ResponseEntity<Void> responseEntity = new ResponseEntity<>(HttpStatus.OK);
        try {
            rubruWebhookService.saveNewEvent(rubruEventVM);
        } catch (Exception e) {
            log.error("error in saving event {}", rubruEventVM, e);
            responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }
}
