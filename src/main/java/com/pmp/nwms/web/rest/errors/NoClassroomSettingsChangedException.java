package com.pmp.nwms.web.rest.errors;

public class NoClassroomSettingsChangedException extends BadRequestAlertException {
    public NoClassroomSettingsChangedException() {
        super(ErrorConstants.NO_CLASSROOM_SETTINGS_CHANGED_TYPE, "No Classroom Settings Changed", "Classroom", "no.classroom.settings.changed");
    }
}
