package com.example.cryptowatcher.timer.jobs;

import com.example.cryptowatcher.service.CryptoService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class TestJob implements Job {
    private final CryptoService cryptoService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        cryptoService.updateActualCryptoPrices();
    }
}
