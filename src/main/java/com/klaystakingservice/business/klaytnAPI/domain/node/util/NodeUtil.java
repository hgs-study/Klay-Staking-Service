package com.klaystakingservice.business.klaytnAPI.domain.node.util;

import com.klaystakingservice.common.util.APIRequest;
import com.klaystakingservice.common.util.BasicRestTemplate;
import com.klaystakingservice.common.util.ConverterUtil;
import com.klaystakingservice.common.util.JsonConverter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class NodeUtil {

    private final APIRequest apiRequest;

    private final BasicRestTemplate basicRestTemplate;

    private final JsonConverter jsonConverter;

    private final ConverterUtil converterUtil;

    //밸런스 확인
    public String getBalance(String address){
        HttpEntity<?> entity = apiRequest.setEntity("klay_getBalance",address);

        RestTemplate restTemplate = basicRestTemplate.get();
        ResponseEntity<String> responseEntity = restTemplate.exchange("https://node-api.klaytnapi.com/v1/klaytn", HttpMethod.POST,entity,String.class);

        return ConverBalance(responseEntity);
    }

    @SneakyThrows
    private String ConverBalance(ResponseEntity<String> responseEntity){
        String hexBalance = jsonConverter.responseEntityToValue(responseEntity,"result");
        return converterUtil.hexToTokenAmount(hexBalance).toString();
    }
}
