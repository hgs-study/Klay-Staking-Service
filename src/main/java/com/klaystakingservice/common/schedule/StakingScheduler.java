package com.klaystakingservice.common.schedule;

import com.klaystakingservice.common.batch.StakingBatchConfig;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

@Component
@Slf4j
@RequiredArgsConstructor
public class StakingScheduler {
    private final StakingBatchConfig stakingBatchConfig;
    private final JobLauncher jobLauncher;

    @Scheduled(cron = "0 0/1 * * * *")
    public void Minute(){
        log.info("==========현재시간(Minute) :=========== "+ new Date());
    }

    @SneakyThrows
    @Scheduled(cron = "0 0/1 * * * *")
    public void transferStakingReward(){
        log.info("==========현재시간(saveGetBalance) :=========== "+ new Date());
        jobLauncher.run(
                stakingBatchConfig.stakingRewardJob(),
                new JobParametersBuilder()
                        .addString("job.name","stakingRewardJob")
                        .addString("version", LocalDateTime.now().toString())
                .toJobParameters()
        );
    }
}
