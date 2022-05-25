package com.pmp.nwms.service.util;

import com.pmp.nwms.NwmsConfig;
import org.apache.commons.codec.binary.Base64;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.nio.charset.StandardCharsets;

public class SignalServerRestCallUtil {
    public static HttpHeaders makeHttpHeaders(MediaType contentType, NwmsConfig nwmsConfig) {
        HttpHeaders headers = new HttpHeaders();
        String auth = nwmsConfig.getSignalServerUsername().trim() + ":" + nwmsConfig.getSignalServerPassword().trim();
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.UTF_8));
        String authHeader = "Basic " + new String(encodedAuth);
        headers.set("authorization", authHeader);
        headers.setContentType(contentType);
        return headers;
    }
}
