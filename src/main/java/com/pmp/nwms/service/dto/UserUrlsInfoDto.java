package com.pmp.nwms.service.dto;

import java.io.Serializable;

public interface UserUrlsInfoDto extends Serializable {
    String getReturnUrl();

    String getWsUrl();

    String getSpecialLink();

    String getAppUrl();

    Integer getQualityVeryLow();

    Integer getQualityLow();

    Integer getQualityMedium();

    Integer getQualityHigh();

    Integer getQualityVeryHigh();

    Boolean getModeratorAutoLogin();
}
