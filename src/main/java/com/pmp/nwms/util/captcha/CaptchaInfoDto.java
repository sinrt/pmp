package com.pmp.nwms.util.captcha;

import java.io.Serializable;

public class CaptchaInfoDto implements Serializable {
    private String clientKey;
    private String captchaImageFileContent;

    public String getClientKey() {
        return clientKey;
    }

    public void setClientKey(String clientKey) {
        this.clientKey = clientKey;
    }

    public String getCaptchaImageFileContent() {
        return captchaImageFileContent;
    }

    public void setCaptchaImageFileContent(String captchaImageFileContent) {
        this.captchaImageFileContent = captchaImageFileContent;
    }
}
