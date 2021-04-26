package com.klaystakingservice.common.util;

import com.klaystakingservice.business.token.application.TokenRepository;
import com.klaystakingservice.common.error.code.ErrorCode;
import com.klaystakingservice.common.error.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.DecoderException;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class ConverterUtil {

    private final TokenRepository tokenRepository;

    public BigDecimal hexToTokenAmount(String hexAmount) throws DecoderException, UnsupportedEncodingException {
        long amount = Long.parseLong(hexAmount.replace("0x",""),16);
        int decimal = tokenRepository.findBySymbol("KLAY")
                                     .orElseThrow(()-> new BusinessException(ErrorCode.TOKEN_NOT_FOUND))
                                     .getDecimal();
        BigDecimal powDecimal = new BigDecimal(Math.pow(10, decimal));

        return new BigDecimal(amount).divide(powDecimal,4 ,BigDecimal.ROUND_DOWN);
    }
}
