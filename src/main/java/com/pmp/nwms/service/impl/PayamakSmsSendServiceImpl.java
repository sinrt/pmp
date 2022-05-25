package com.pmp.nwms.service.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.payamak.panel.client.SendSMSService;
import com.payamak.panel.client.SendSimpleSMS2ResultCodes;
import com.pmp.nwms.NwmsConfig;
import com.pmp.nwms.service.SmsSendService;
import com.pmp.nwms.web.rest.errors.PayamakSmsSendException;
import com.pmp.nwms.web.rest.errors.SmsServiceNotReadyException;
import com.squareup.okhttp.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class PayamakSmsSendServiceImpl implements SmsSendService {

    private final Logger log = LoggerFactory.getLogger(PayamakSmsSendServiceImpl.class);

    @Autowired
    private NwmsConfig nwmsConfig;

    private SendSMSService sendSMSService;

    private boolean smsServiceReady = false;

    @PostConstruct
    public void init() {
        try {
            sendSMSService = new SendSMSService(nwmsConfig.getSendSMSServiceWsdlUrl());
            smsServiceReady = true;
        } catch (Exception e) {
//            throw new RuntimeException(e);
            log.warn(e.getMessage(), e);
            smsServiceReady = false;
        }
    }

    @Override
    public String sendMessage(String receiver, String content) {
        if (nwmsConfig.isSendSMSServiceUseSoap()) {
            if(!smsServiceReady){
                throw new SmsServiceNotReadyException();
            }
            String result = sendSMSService.getSendSoap().sendSimpleSMS2(nwmsConfig.getSendSMSServiceUsername(), nwmsConfig.getSendSMSServicePassword(), receiver, nwmsConfig.getSendSMSServiceFromNumber(), content, nwmsConfig.getSendSMSServiceIsFlash());
            log.info("send message using soap result is : " + result);
            SendSimpleSMS2ResultCodes resultCode = SendSimpleSMS2ResultCodes.findByCode(result);
            if (resultCode == null) {
                return result;
            } else {
                throw new PayamakSmsSendException(resultCode);
            }
        } else {
            try {
                OkHttpClient client = new OkHttpClient();
                client.setConnectTimeout(60, TimeUnit.SECONDS); // connect timeout
                client.setReadTimeout(60, TimeUnit.SECONDS);    // socket timeout
                MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");

                RequestBody body =
                    RequestBody.create(mediaType,
                        "username=" + nwmsConfig.getSendSMSServiceUsername()
                            + "&password=" + nwmsConfig.getSendSMSServicePassword()
                            + "&to=" + receiver
                            + "&from=" + nwmsConfig.getSendSMSServiceFromNumber()
                            + "&text=" + content
                            + "&isflash=" + nwmsConfig.getSendSMSServiceIsFlash());
                Request reqToSetSentCode = new Request.Builder()
                    .url(nwmsConfig.getSendSMSServiceRestUrl())
                    .post(body)
                    .addHeader("cache-control", "no-cache")
                    .addHeader("postman-token", "c26ca3b0-9f44-3cdf-9da3-60c86a9f75b3")
                    .addHeader("content-type", "application/x-www-form-urlencoded")
                    .build();

                Response response = client.newCall(reqToSetSentCode).execute();
                String responseBody = response.body().string();
                log.info("send message using rest result is " + responseBody);
                if (response.isSuccessful()) {
                    Gson gson = new Gson();
                    Type type = new TypeToken<Map<String, String>>() {
                    }.getType();
                    Map<String, String> resultMap = gson.fromJson(responseBody, type);
                    String retStatus = resultMap.get("RetStatus");
                    if (retStatus.equalsIgnoreCase(SendSimpleSMS2ResultCodes.SuccessfullyRequested.getCode())) {
                        return resultMap.get("Value");
                    } else {
                        log.error("error in calling sms send service " + retStatus + ", response body is " + responseBody);
                        SendSimpleSMS2ResultCodes resultCode = SendSimpleSMS2ResultCodes.findByCode(retStatus);
                        if(resultCode == null){
                            resultCode = SendSimpleSMS2ResultCodes.Unknown;
                        }
                        throw new PayamakSmsSendException(resultCode);
                    }
                } else {
                    log.error("error in calling sms send service " + response.code() + ", response body is " + responseBody);
                    throw new PayamakSmsSendException(SendSimpleSMS2ResultCodes.Unknown);
                }
            } catch (PayamakSmsSendException e) {
                throw e;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
