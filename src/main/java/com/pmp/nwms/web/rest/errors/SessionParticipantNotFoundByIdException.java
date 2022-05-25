package com.pmp.nwms.web.rest.errors;

public class SessionParticipantNotFoundByIdException extends BadRequestAlertException {
    public SessionParticipantNotFoundByIdException(String sessionId, Long id, String participantId) {
        super(ErrorConstants.SESSION_PARTICIPANT_NOT_FOUND_BY_ID_TYPE, "Participant by id " + participantId + " not found in session by id " + sessionId + " saved by id  " + id, "rubruwebhook", "participantnotfount");
    }
}
