package com.pmp.nwms.logging.util;

import com.pmp.nwms.logging.data.entity.Log;
import com.pmp.nwms.security.NwmsUser;
import com.pmp.nwms.util.UserUtil;
import com.pmp.nwms.web.ClientInfo;
import org.apache.commons.lang3.ObjectUtils;

import java.util.Date;

public class LogUtil {
    private static final String UNKNOWN = "UNKNOWN";

    public static Log makePlainLogObject(String logSource, String logUri, ClientInfo clientInfo, String appWebUrl) {
        Log log = new Log();
        String userName = "Anonymous";
        NwmsUser currentUser = UserUtil.getCurrentUser();
        if (ObjectUtils.anyNotNull(currentUser)) {
            userName = currentUser.getUsername();
        }

        if (clientInfo == null) {
            clientInfo = ClientInfo.UNKNOWN;
        }

        log.setAppWebUrl(appWebUrl);
        log.setUserName(userName);

        log.setIpAddress(clientInfo.getIp());
        log.setBrowserName(clientInfo.getBrowserName());
        log.setBrowserType(clientInfo.getBrowserType());
        log.setBrowserVersion(clientInfo.getBrowserVersion());
        log.setBrowserMajorVersion(clientInfo.getBrowserMajorVersion());
        log.setDeviceType(clientInfo.getDeviceType());
        log.setPlatformName(clientInfo.getPlatformName());
        log.setPlatformVersion(clientInfo.getPlatformVersion());

        log.setLogSource(logSource);
        log.setLogUri(logUri);
        return log;
    }

}
