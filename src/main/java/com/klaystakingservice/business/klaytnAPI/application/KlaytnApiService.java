package com.klaystakingservice.business.klaytnAPI.application;

import com.klaystakingservice.business.account.entity.Account;
import com.klaystakingservice.business.klaytnAPI.domain.node.application.NodeHistoryRepository;
import com.klaystakingservice.business.klaytnAPI.domain.node.entity.NodeHistory;
import com.klaystakingservice.business.klaytnAPI.domain.transaction.application.TransactionHistoryRepository;
import com.klaystakingservice.business.klaytnAPI.domain.transaction.entity.TransactionHistory;
import com.klaystakingservice.business.klaytnAPI.domain.transaction.form.TransactionForm;
import com.klaystakingservice.business.klaytnAPI.entity.KlaytnAPI;
import com.klaystakingservice.business.token.application.TokenRepository;
import com.klaystakingservice.common.error.code.ErrorCode;
import com.klaystakingservice.common.error.exception.BusinessException;
import com.klaystakingservice.common.util.ConverterUtil;
import com.klaystakingservice.common.util.JsonConverter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.codec.DecoderException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
@RequiredArgsConstructor
public class KlaytnApiService {


    private final KlaytnAPIRepository klaytnAPIRepository;

    private final NodeHistoryRepository nodeHistoryRepository;

    private final TransactionHistoryRepository transactionHistoryRepository;

    private final TokenRepository tokenRepository;

    private final JsonConverter jsonConverter;

    private final ConverterUtil converterUtil;

    private static final String TRANSACTION_TARGET = "transaction";



    public void saveApiHistory(Account account, String apiTarget, ResponseEntity<String> responseEntity) {
        KlaytnAPI klaytnAPI = klaytnAPIRepository.findByTarget(apiTarget).orElseThrow(() -> new BusinessException(ErrorCode.API_TARGET_NOT_FOUND));

        nodeHistoryRepository.save(NodeHistory.builder()
                                              .account(account)
                                              .klaytnAPI(klaytnAPI)
                                              .build());

        saveWhenTransactionAPI(account,apiTarget, responseEntity,klaytnAPI);
    }

    @SneakyThrows
    private void saveWhenTransactionAPI(Account account, String apiTarget, ResponseEntity<String> responseEntity, KlaytnAPI klaytnAPI) {
        if (TRANSACTION_TARGET.equals(apiTarget)) {
            saveTransaction(account, setTransactionDTO(responseEntity), klaytnAPI);
        }
    }

    private void saveTransaction(Account account,TransactionForm.Request.Add add, KlaytnAPI klaytnAPI) throws DecoderException, UnsupportedEncodingException {

        transactionHistoryRepository.save(TransactionHistory.builder()
                                                     .account(account)
                                                     .fromAddress(add.getFromAddress())
                                                     .toAddress(add.getToAddreess())
                                                     .transaction(add.getTransaction())
                                                     .amount(add.getAmount())
                                                     .klaytnAPI(klaytnAPI)
                                                     .build());
    }

    private TransactionForm.Request.Add setTransactionDTO(ResponseEntity<String> responseEntity) throws DecoderException, UnsupportedEncodingException {
        return TransactionForm.Request.Add.builder()
                                          .transaction(jsonConverter.responseEntityToValue(responseEntity, "transactionHash"))
                                          .fromAddress(jsonConverter.responseEntityToValue(responseEntity, "from"))
                                          .toAddress(jsonConverter.responseEntityToValue(responseEntity, "to"))
                                          .amount(converterUtil.hexToTokenAmount(jsonConverter.responseEntityToValue(responseEntity, "value")))
                                          .build();
    }
}

