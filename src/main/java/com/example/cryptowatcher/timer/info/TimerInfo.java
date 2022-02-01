package com.example.cryptowatcher.timer.info;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class TimerInfo {
    private boolean runForever;
    private long repeatIntervalMs;
    private long initialOffsetMs;
}
