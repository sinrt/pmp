package com.pmp.nwms.service.dto;

public class UserContainer {

    private String fullName;
    private String status;
    private String simagooToken;

    public UserContainer() {
    }

    public UserContainer(String fullName, String status, String simagooToken) {
        this.fullName = fullName;
        this.status = status;
        this.simagooToken = simagooToken;
    }

    public String getSimagooToken() {
        return simagooToken;
    }

    public void setSimagooToken(String simagooToken) {
        this.simagooToken = simagooToken;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
