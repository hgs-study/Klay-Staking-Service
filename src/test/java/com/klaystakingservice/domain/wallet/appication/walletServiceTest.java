package com.klaystakingservice.domain.wallet.appication;

import com.klaystakingservice.common.util.BasicRestTemplate;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith({MockitoExtension.class})
class walletServiceTest {

    @Spy
    private BasicRestTemplate basicRestTemplate;

    @Value("${klaytn.header.authorization}")
    private String authorization;

    @Value("${klaytn.header.x-chain-id}")
    private String xChainId;

    @Value("${klaytn.header.x-krn}")
    private String xKrn;

    @Test
    @DisplayName("Wallet 생성 API 확인")
    public void createWallet(){
        //given
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization",authorization);
        headers.set("x-chain-id",xChainId);
        headers.set("x-krn",xKrn);
        HttpEntity<?> entity = new HttpEntity<>(headers);
        RestTemplate restTemplate = basicRestTemplate.get();

        //when
        ResponseEntity<String> resultString = restTemplate.exchange("https://wallet-api.klaytnapi.com/v2/account", HttpMethod.POST,entity,String.class);
        JSONObject jsonObject = new JSONObject(resultString);
        String body = jsonObject.getString("body");
        JSONObject bodyJsonObject = new JSONObject(body);
        String address = bodyJsonObject.getString("address");

        //then
        assertThat(address.length()).isEqualTo(42);
    }
}
