package com.klaystakingservice.domain.klaytnAPI.domain.transaction.util;

import com.klaystakingservice.business.klaytnAPI.domain.transaction.util.TransactionUtil;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class TransactionUtilTest {

    @Autowired
    private TransactionUtil transactionUtil;

    @Test
    @DisplayName("0.1 Klay 보상 지급")
    public void transferRewardKlayTest(){
        //given
        String toAddress = "0xB8aDd84c85Dac5b7eb86F5aa70B86AAe916716fE"; //Test 지갑주소

        //when
        ResponseEntity<String> transactionResponse = transactionUtil.RewardKlayWhenJoin(toAddress);

        JSONObject jsonObject = new JSONObject(transactionResponse);
        String responseBody = jsonObject.getString("body");

        JSONObject responseBodyJsonObject = new JSONObject(responseBody);
        String responseToAddress = responseBodyJsonObject.getString("to");

        //then
        assertThat(toAddress.toLowerCase()).isEqualTo(responseToAddress);
    }

}
