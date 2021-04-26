package com.klaystakingservice.common.util;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class APIRequest {
    @Value("${klaytn.header.authorization}")
    private String authorization;

    @Value("${klaytn.header.x-chain-id}")
    private String xChainId;

    @Value("${klaytn.header.Content-Type}")
    private String contentType;

    private final BasicRestTemplate basicRestTemplate;

    public HttpHeaders setHeader() {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization",authorization);
        headers.set("x-chain-id",xChainId);
        headers.set("Content-Type",contentType);

        return headers;
    }

    public String setBodyGetBalance(String method, String address) {

        String body = "{" +
                        "\"method\":\""+method+"\" ," +
                        "\"id\":"+1+" ," +
                        "\"params\": [\""+address+"\",\"latest\"]" +
                     "}";

        return body;
    }

    public HttpEntity<?> setEntity(String method,String address){
        HttpHeaders headers = setHeader();
        String body = setBodyGetBalance(method,address);
        HttpEntity<?> entity = new HttpEntity<>(body,headers);
        return new HttpEntity<>(body,headers);
    }

}
