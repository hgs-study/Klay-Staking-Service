package com.klaystakingservice;


import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableBatchProcessing
@SpringBootApplication
@EnableJpaAuditing
@EnableCaching
public class KlayStakingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(KlayStakingServiceApplication.class, args);
    }
}
