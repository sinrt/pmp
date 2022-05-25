package com.pmp.nwms.web.rest.vm;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class DynamicStudentDataVM implements Serializable {
    @NotNull
    @Size(min = 1)
    private String token;
    @NotNull
    @Size(min = 1)
    private String fullName;

    @Size(max = 50)
    private String participantKey;
    @NotNull
    @Size(min = 1)
    private String checksum;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getParticipantKey() {
        return participantKey;
    }

    public void setParticipantKey(String participantKey) {
        this.participantKey = participantKey;
    }

    public String getChecksum() {
        return checksum;
    }

    public void setChecksum(String checksum) {
        this.checksum = checksum;
    }


    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("t=");
        result.append(token).append("&fn=").append(fullName);
        if (participantKey != null && !participantKey.isEmpty()) {
            result.append("&pk=").append(participantKey);
        }
        result.append("&");
        return result.toString();
    }
}
