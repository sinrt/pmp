package com.pmp.nwms.web.rest.errors;

public class EntityNotFountByCodeException extends BadRequestAlertException {
    public EntityNotFountByCodeException(String entityName, Object code) {
        super(ErrorConstants.ENTITY_NOT_FOUND_TYPE, entityName + " not found by code " + code, entityName + "NotFound", entityName + "NotFound");
    }
}
