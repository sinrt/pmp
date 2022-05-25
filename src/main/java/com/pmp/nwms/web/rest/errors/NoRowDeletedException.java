package com.pmp.nwms.web.rest.errors;

public class NoRowDeletedException extends BadRequestAlertException {
    public NoRowDeletedException(String entityName, Long entityId, Long ownerId) {
        super(ErrorConstants.NO_ENTITY_DELETED_TYPE, "No " + entityName + " by id " + entityId + " for user " + ownerId + " found to be deleted.", entityName + "Management", entityName+ ".not.found");
    }
}
