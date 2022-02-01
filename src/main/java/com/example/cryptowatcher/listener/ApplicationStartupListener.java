package com.example.cryptowatcher.listener;

import com.example.cryptowatcher.timer.info.TimerInfo;
import com.example.cryptowatcher.timer.jobs.TestJob;
import com.example.cryptowatcher.timer.service.SchedulerService;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ApplicationStartupListener {
    private final SchedulerService schedulerService;
    private static final long repeatIntervalMs = 60000;

    @EventListener(ApplicationReadyEvent.class)
    public void onStartup() {
        final TimerInfo timerInfo = TimerInfo.builder()
                .repeatIntervalMs(repeatIntervalMs)
                .runForever(true)
                .build();
        schedulerService.scheduleJob(TestJob.class, timerInfo);
    }
}
