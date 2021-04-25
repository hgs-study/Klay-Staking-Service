package com.klaystakingservice.business.klaytnAPI.domain.transaction.form;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

public class TransactionForm {
    public static class Request{

        @Getter
        @NoArgsConstructor
        public static class Add{
            private String transaction;
            private String fromAddress;
            private String toAddreess;
            private BigDecimal amount;

            @Builder
            private Add(String transaction,String fromAddress, String toAddress, BigDecimal amount){
                this.transaction = transaction;
                this.fromAddress = fromAddress;
                this.toAddreess = toAddress;
                this.amount = amount;
            }
        }
    }
}
