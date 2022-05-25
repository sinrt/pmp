package com.pmp.nwms.web.rest;

import com.pmp.nwms.security.AuthoritiesConstants;
import com.pmp.nwms.service.RubruSessionManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RubruSessionManagementResource {

    @Autowired
    private RubruSessionManagementService rubruSessionManagementService;

    @PostMapping("/publish/{sessionId}/{userIdentifier}")
    @Secured(AuthoritiesConstants.OUTER_MANAGER)
    public ResponseEntity<Boolean> publish(@PathVariable("sessionId") String sessionId, @PathVariable("userIdentifier") String userIdentifier) {
        try {
            Long userId = Long.valueOf(userIdentifier);
            rubruSessionManagementService.sendPublishSignal(sessionId, userId);
        } catch (NumberFormatException e) {
            rubruSessionManagementService.sendPublishSignal(sessionId, userIdentifier);
        }
        return ResponseEntity.ok(true);
    }

    @PostMapping("/unpublish/{sessionId}/{userIdentifier}")
    @Secured(AuthoritiesConstants.OUTER_MANAGER)
    public ResponseEntity<Boolean> unpublish(@PathVariable("sessionId") String sessionId, @PathVariable("userIdentifier") String userIdentifier) {
        try {
            Long userId = Long.valueOf(userIdentifier);
            rubruSessionManagementService.sendUnpublishSignal(sessionId, userId);
        } catch (NumberFormatException e) {
            rubruSessionManagementService.sendUnpublishSignal(sessionId, userIdentifier);
        }
        return ResponseEntity.ok(true);
    }

    @PostMapping("/close/session/{sessionId}")
    @Secured(AuthoritiesConstants.OUTER_MANAGER)
    public ResponseEntity<Boolean> closeSession(@PathVariable("sessionId") String sessionId) {
        rubruSessionManagementService.closeSession(sessionId);
        return ResponseEntity.ok(true);
    }
}
