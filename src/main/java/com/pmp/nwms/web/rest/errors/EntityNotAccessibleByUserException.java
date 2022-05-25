package com.pmp.nwms.web.rest.errors;

public class EntityNotAccessibleByUserException extends BadRequestAlertException {
    public EntityNotAccessibleByUserException(String entityName, Long id) {
        super(ErrorConstants.ENTITY_NOT_ACCESSIBLE_TYPE, entityName + " by id " + id + " not accessible by user!", entityName + "NotAccessible", entityName + "NotAccessible");
    }
}
