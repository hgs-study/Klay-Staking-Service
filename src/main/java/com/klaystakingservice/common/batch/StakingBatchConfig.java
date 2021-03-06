package com.klaystakingservice.common.batch;


import com.klaystakingservice.business.klaytnAPI.application.KlaytnAPIRepository;
import com.klaystakingservice.business.klaytnAPI.application.KlaytnApiService;
import com.klaystakingservice.business.klaytnAPI.domain.node.application.NodeHistoryRepository;
import com.klaystakingservice.business.klaytnAPI.domain.node.util.NodeUtil;
import com.klaystakingservice.business.klaytnAPI.domain.transaction.application.TransactionHistoryRepository;
import com.klaystakingservice.business.klaytnAPI.domain.transaction.entity.TransactionHistory;
import com.klaystakingservice.business.klaytnAPI.domain.transaction.util.TransactionUtil;
import com.klaystakingservice.business.order.domain.product.entity.OrderedProduct;
import com.klaystakingservice.business.staking.domain.rewardHistory.application.StakingRewardHistoryRepository;
import com.klaystakingservice.business.staking.domain.rewardHistory.entity.StakingRewardHistory;
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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class StakingBatchConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory emf;
    private final TransactionUtil transactionUtil;
    private final StakingRewardHistoryRepository stakingRewardHistoryRepository;

    private static final int chunkSize = 10;
    private static final String BEAN_PREFIX = "getWalletAddress_";


    @Bean
    public Job stakingRewardJob(){
        return jobBuilderFactory.get("stakingRewardJob")
                                .start(stakingRewardStep())
                                .build();
    }

    @Bean
    public Step stakingRewardStep(){
        return stepBuilderFactory.get("stakingRewardStep")
                                 .<OrderedProduct, StakingRewardHistory>chunk(chunkSize)
                                 .reader(stakingRewardReader())
                                 .processor(stakingRewardProcessor())
                                 .writer(stakingRewardWriter())
                                 .build();
    }

    @Bean
    public JpaPagingItemReader<OrderedProduct> stakingRewardReader(){
        return new JpaPagingItemReaderBuilder<OrderedProduct>()
                .name(BEAN_PREFIX+"reader")
                .entityManagerFactory(emf)
                .pageSize(chunkSize)
                .queryString("select a from OrderedProduct a where a.expireStatus = false")
                .build();
    }

    @Bean
    public ItemProcessor<OrderedProduct, StakingRewardHistory> stakingRewardProcessor(){
        return OrderedProduct-> {
            transactionUtil.stakingRewardKlay(OrderedProduct);

            return StakingRewardHistory.builder()
                                       .orderedProduct(OrderedProduct)
                                       .rewardAmount(OrderedProduct.getOrder().getStaking().getRewardAmount())
                                       .build();
        };
    }

    @Bean
    public ItemWriter<StakingRewardHistory> stakingRewardWriter(){
        return StakingRewardHistory -> {
            System.out.println("111");
            StakingRewardHistory.stream()
                                .map(T -> stakingRewardHistoryRepository.save(T))
                                .forEach(T -> System.out.println("T.getOrderedProduct() = " + T.getOrderedProduct()));
        };
    }



}
