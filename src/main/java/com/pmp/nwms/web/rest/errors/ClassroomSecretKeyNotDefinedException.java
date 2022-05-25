package com.pmp.nwms.web.rest.errors;

public class ClassroomSecretKeyNotDefinedException extends BadRequestAlertException {
    public ClassroomSecretKeyNotDefinedException() {
        super(ErrorConstants.CLASSROOM_SECRET_KEY_NOT_DEFINED_TYPE, "ClassroomSecretKeyNotDefinedException", "ClassroomSecretKeyNotDefined", "ClassroomSecretKeyNotDefined");
    }

}
