package com.pmp.nwms.web.rest.errors;

public class ParticipantNotFoundInSessionException extends BadRequestAlertException {
    public ParticipantNotFoundInSessionException() {
        super(ErrorConstants.PARTICIPANT_NOT_FOUND_IN_SESSION_TYPE, "Participant not found in session.", "participantNotFoundInSession", "participantNotFoundInSession");
    }
}
