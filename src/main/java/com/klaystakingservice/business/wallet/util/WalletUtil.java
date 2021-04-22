package com.klaystakingservice.business.wallet.util;

import com.klaystakingservice.common.util.BasicRestTemplate;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class WalletUtil {

    @Value("${klaytn.header.authorization}")
    private String authorization;

    @Value("${klaytn.header.x-chain-id}")
    private String xChainId;

    @Value("${klaytn.header.x-krn}")
    private String xKrn;

    private final BasicRestTemplate basicRestTemplate;

    public String create(){

        HttpEntity<?> entity = setHeader();
        RestTemplate restTemplate = basicRestTemplate.get();
        ResponseEntity<String> resultString = restTemplate.exchange("https://wallet-api.klaytnapi.com/v2/account", HttpMethod.POST,entity,String.class);

        return getAddress(resultString);
    }

    private String getAddress(ResponseEntity<String> resultString) {

        JSONObject jsonObject = new JSONObject(resultString);
        String body = jsonObject.getString("body");
        
        JSONObject bodyJsonObject = new JSONObject(body);
        String address = bodyJsonObject.getString("address");
        System.out.println("address = " + address);
        return address;
    }

    private HttpEntity<?> setHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization",authorization);
        headers.set("x-chain-id",xChainId);
        headers.set("x-krn",xKrn);
        HttpEntity<?> entity = new HttpEntity<>(headers);
        return entity;
    }
}
