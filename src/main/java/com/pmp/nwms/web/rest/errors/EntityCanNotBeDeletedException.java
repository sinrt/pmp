package com.pmp.nwms.web.rest.errors;

public class EntityCanNotBeDeletedException extends BadRequestAlertException {
    public EntityCanNotBeDeletedException(String entityName, Long entityId, String relatedEntityName) {
        super(ErrorConstants.ENTITY_CAN_NOT_BE_DELETED_TYPE, entityName + " by id " + entityId + " can not be deleted due to existence of " + relatedEntityName, entityName, "related.entity.exists");
    }
}
