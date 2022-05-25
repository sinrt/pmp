package com.pmp.nwms.service.dto;

public class RegistryContainer {

    private CallStatus callStatus;
    private String peerUser;
    private String simagooToken;
    private String fullName;
    private String jwtToken;
    private Long userId;

    public RegistryContainer() {
    }

    public RegistryContainer(String simagooToken, String fullName) {
        this.simagooToken = simagooToken;
        this.fullName = fullName;
    }

    public RegistryContainer(CallStatus callStatus, String simagooToken, String fullName) {
        this.callStatus = callStatus;
        this.simagooToken = simagooToken;
        this.fullName = fullName;
    }

    public RegistryContainer(CallStatus callStatus, String peerUser) {
        this.callStatus = callStatus;
        this.peerUser = peerUser;
    }

    public RegistryContainer(String simagooToken, String fullName, String jwtToken,Long id) {
        this.simagooToken = simagooToken;
        this.fullName = fullName;
        this.jwtToken = jwtToken;
        this.userId=id;
    }

    public RegistryContainer(CallStatus callStatus, String peerUser, String simagooToken, String fullName) {
        this.callStatus = callStatus;
        this.peerUser = peerUser;
        this.simagooToken = simagooToken;
        this.fullName = fullName;
    }

    public RegistryContainer(CallStatus callStatus) {
        this.callStatus = callStatus;
    }

    public CallStatus getCallStatus() {
        return callStatus;
    }

    public void setCallStatus(CallStatus callStatus) {
        this.callStatus = callStatus;
    }

    public String getPeerUser() {
        return peerUser;
    }

    public void setPeerUser(String peerUser) {
        this.peerUser = peerUser;
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

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
