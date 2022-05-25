package com.pmp.nwms.web.rest.errors;

public class EntityNotFountByIdException extends BadRequestAlertException {
    public EntityNotFountByIdException(String entityName, Object id) {
        super(ErrorConstants.ENTITY_NOT_FOUND_TYPE, entityName + " not found by id " + id, entityName + "NotFound", entityName + "NotFound");
    }
}
