package com.klaystakingservice.business.klaytnAPI.domain.transaction.util;

import com.klaystakingservice.business.account.application.AccountRepository;
import com.klaystakingservice.business.account.entity.Account;
import com.klaystakingservice.business.klaytnAPI.application.KlaytnApiService;
import com.klaystakingservice.common.error.code.ErrorCode;
import com.klaystakingservice.common.error.exception.BusinessException;
import com.klaystakingservice.common.util.BasicRestTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

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
    private String AdminAddress;

    private final BasicRestTemplate basicRestTemplate;

    private final AccountRepository accountRepository;

    private final KlaytnApiService klaytnApiService;

    private static final BigDecimal ZeroPointOneKlay = new BigDecimal("10000000000000000"); //0.01 klay

    //회원가입시 0. 1 Klay 보상 지급
    public ResponseEntity<String> signUpRewardKlay(String toAddress){

        return  transferKlay(accountRepository.findByEmail("ADMIN").orElseThrow(()-> new BusinessException(ErrorCode.EMAIL_NOT_FOUND))
                            ,AdminAddress
                            , toAddress
                            , ZeroPointOneKlay);
    }

    public ResponseEntity<String> transferKlay(Account account, String fromAddress, String toAddress, BigDecimal amount){
        RestTemplate restTemplate = basicRestTemplate.get();

        HttpHeaders headers = setHeader();
        String body = setBody(fromAddress, toAddress,amount);

        HttpEntity<?> entity = new HttpEntity<>(body, headers);
        ResponseEntity<String> resultString = restTemplate.postForEntity("https://wallet-api.klaytnapi.com/v2/tx/value", entity, String.class);

        klaytnApiService.saveApiHistory(account,"transaction",resultString);

        return resultString;
    }

    private String setBody(String fromAddress, String toAddress,BigDecimal amount) {

        String body = "{" +
                        "\"from\":\""+fromAddress+"\" ," +
                        "\"to\":\""+ toAddress +"\" ," +
                        "\"value\":\"0x"+ amount.toBigInteger().toString(16)+"\" ," +
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
