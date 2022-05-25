package com.pmp.nwms.web.rest.errors;

public class EntityCanNotBeEditedException extends BadRequestAlertException {
    public EntityCanNotBeEditedException(String entityName, Object entityId) {
        super(ErrorConstants.ENTITY_CAN_NOT_BE_EDITED_TYPE, "Entity " + entityName + " by id " + entityId + " can not be edited.", entityName, "entityCanNotBeEdited");
    }
}
