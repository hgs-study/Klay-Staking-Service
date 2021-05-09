package com.klaystakingservice.common.batch;

import com.batchschedulerbasic.application.TransactionApiHistoryRepository;
import com.batchschedulerbasic.common.AWS.TransactionUtil;
import com.batchschedulerbasic.entity.TransactionApiHistory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class AwsBatchConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory emf;
    private final TransactionUtil transactionUtil;
    private final TransactionApiHistoryRepository transactionApiHistoryRepository;

    private static final int chunkSize = 10;
    private static final String BEAN_PREFIX = "getWalletAddress_";

    @Value("${klaytn.wallet.tx.from.address}")
    private String AdminAddress;

    @Bean
    public Job getWalletAddressJob(){
        return jobBuilderFactory.get("getWalletAddressJob")
                .start(getWalletAddressStep())
                .build();
    }

    @Bean
    public Step getWalletAddressStep(){
        System.out.println("==========getWalletAddressStep() start=========");
        return stepBuilderFactory.get("getWalletAddressStep")
                                .<TransactionApiHistory, TransactionApiHistory>chunk(chunkSize)
                                .reader(getWalletAddressReader())
                                .processor(getWalletAddressProcessor())
                                .writer(getWalletAddressWriter())
                                .build();
    }

    @Bean
    public JpaPagingItemReader<TransactionApiHistory> getWalletAddressReader(){
        return new JpaPagingItemReaderBuilder<TransactionApiHistory>()
                .name(BEAN_PREFIX+"reader")
                .entityManagerFactory(emf)
                .pageSize(chunkSize)
                .queryString("select a from TransactionApiHistory a")
                .build();
    }

    @Bean
    public ItemProcessor<TransactionApiHistory, TransactionApiHistory> getWalletAddressProcessor(){
        return TransactionApiHistory-> {
            TransactionApiHistory.setBalance(transactionUtil.getBalance(TransactionApiHistory.getAddress()));
            return TransactionApiHistory;
        };
    }

    @Bean
    public ItemWriter<TransactionApiHistory> getWalletAddressWriter(){
        AtomicInteger count = new AtomicInteger(0);
        return TransactionApiHistory -> {
            TransactionApiHistory.stream()
                    .map(T -> transactionApiHistoryRepository.save(T))
                    .forEach(T -> {
                        System.out.println("========="+count.incrementAndGet()+"번째 ==================");
                        System.out.println("T.getApiName() = " + T.getApiName());
                        System.out.println("T.getBalance() = " + T.getBalance());
                    });
        };
    }



}
