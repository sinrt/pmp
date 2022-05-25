package com.pmp.nwms.web.rest.errors;

public class NoClassroomSettingsSentException extends BadRequestAlertException {
    public NoClassroomSettingsSentException() {
        super(ErrorConstants.NO_CLASSROOM_SETTINGS_SENT_TYPE, "No Classroom Settings Sent", "Classroom", "no.classroom.settings.sent");
    }
}
