package com.klaystakingservice.business.klaytnAPI.domain.transaction.util;

import com.klaystakingservice.business.account.application.AccountRepository;
import com.klaystakingservice.business.account.application.AccountService;
import com.klaystakingservice.business.account.entity.Account;
import com.klaystakingservice.business.klaytnAPI.application.KlaytnApiService;
import com.klaystakingservice.business.klaytnAPI.entity.KlaytnAPI;
import com.klaystakingservice.business.klaytnAPI.enumerated.Target;
import com.klaystakingservice.business.order.domain.product.application.OrderedProductService;
import com.klaystakingservice.business.order.domain.product.entity.OrderedProduct;
import com.klaystakingservice.business.token.application.TokenService;
import com.klaystakingservice.business.wallet.application.WalletService;
import com.klaystakingservice.common.error.code.ErrorCode;
import com.klaystakingservice.common.error.exception.BusinessException;
import com.klaystakingservice.common.util.BasicRestTemplate;
import com.klaystakingservice.common.util.JsonConverter;
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

    private final AccountService accountService;

    private final KlaytnApiService klaytnApiService;

    private final TokenService tokenService;

    private final WalletService walletService;

    private static final BigDecimal ZeroPointOneKlay = new BigDecimal("10000000000000000"); //0.01 klay

    //회원가입시 0. 1 Klay 보상 지급
    public ResponseEntity<String> RewardKlayWhenJoin(String toAddress){

        return  transferKlay(accountService.findByEmail("ADMIN"),
                             AdminAddress,
                             toAddress,
                             ZeroPointOneKlay);
    }

    //회원가입시 0. 1 Klay 보상 지급
    public void stakingRewardKlay(OrderedProduct orderedProduct){

        transferKlay(accountService.findByEmail("ADMIN"),
                     AdminAddress,
                     walletService.findByAccount(orderedProduct.getOrder().getAccount()).getAddress(),
                     setRewardAmount(orderedProduct)
        );
    }

    //Reward * Token Decimal
    private BigDecimal setRewardAmount(OrderedProduct orderedProduct) {
        BigDecimal decimalPoint = new BigDecimal(10).pow(getKlayDecimal());
        BigDecimal tokenRewardAmount = orderedProduct.getOrder().getStaking().getRewardAmount();

        return getTotalRewardAmount(decimalPoint, tokenRewardAmount);
    }

    private BigDecimal getTotalRewardAmount(BigDecimal decimalPoint, BigDecimal tokenRewardAmount) {
        return tokenRewardAmount.multiply(decimalPoint);
    }

    private int getKlayDecimal() {
        return tokenService.findBySymbol("KLAY").getDecimal();
    }

    public ResponseEntity<String> transferKlay(Account account, String fromAddress, String toAddress, BigDecimal amount){
        RestTemplate restTemplate = basicRestTemplate.get();

        HttpHeaders headers = setHeader();
        String body = setBody(fromAddress, toAddress,amount);
        HttpEntity<?> entity = new HttpEntity<>(body, headers);
        ResponseEntity<String> resultString = restTemplate.postForEntity("https://wallet-api.klaytnapi.com/v2/tx/value", entity, String.class);

        KlaytnAPI klaytnAPI = klaytnApiService.findByTarget(Target.TRANSACTION);
        klaytnApiService.saveApiHistory(account, klaytnAPI, resultString);

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
