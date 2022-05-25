package com.pmp.nwms.service;

public interface SmsSendService {
    String sendMessage(String receiver, String content);
}
