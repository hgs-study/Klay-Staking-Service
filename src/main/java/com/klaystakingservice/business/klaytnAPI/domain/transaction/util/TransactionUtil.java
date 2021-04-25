package com.klaystakingservice.business.klaytnAPI.domain.transaction.util;

import com.klaystakingservice.common.util.BasicRestTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
@Slf4j
public class TransactionUtil {
    @Value("${klaytn.header.authorization}")
    private String authorization;

    @Value("${klaytn.header.x-chain-id}")
    private String xChainId;

    @Value("${klaytn.header.Content-Type}")
    private String contentType;

    @Value("${klaytn.wallet.tx.from.address}")
    private String fromAddress;

    private final BasicRestTemplate basicRestTemplate;

    private static final String ZeroPointOneKlay = "0x2386f26fc10000";

    //회원가입시 0. 1 Klay 보상 지급
    public ResponseEntity<String> signUpRewardKlay(String toAddress){
        RestTemplate restTemplate = basicRestTemplate.get();

        HttpHeaders headers = setHeader();
        String body = setBody(toAddress);

        HttpEntity<?> entity = new HttpEntity<>(body, headers);
        ResponseEntity<String> resultString = restTemplate.postForEntity("https://wallet-api.klaytnapi.com/v2/tx/value", entity, String.class);

        log.debug("resultString ="+resultString);

        return resultString;
    }

    private String setBody(String toAddress) {
        String body = "{" +
                        "\"from\":\""+fromAddress+"\" ," +
                        "\"to\":\""+ toAddress +"\" ," +
                        "\"value\":\""+ZeroPointOneKlay+"\" ," +
                        "\"submit\":"+true+
                      "}";
        return body;
    }

    private HttpHeaders setHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization",authorization);
        headers.set("x-chain-id",xChainId);
        headers.set("Content-Type",contentType);
        return headers;
    }
}
