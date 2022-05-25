package com.pmp.nwms.web.rest.vm;

import javax.validation.constraints.NotNull;

public class FileUploadVM {
    @NotNull
    private String sessionId;
    @NotNull
    private String name;
    @NotNull
    private String contentType;
    @NotNull
    private byte[] content;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
