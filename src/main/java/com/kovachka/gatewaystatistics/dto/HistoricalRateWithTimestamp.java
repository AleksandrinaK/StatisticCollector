package com.kovachka.gatewaystatistics.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class HistoricalRateWithTimestamp {
    private String rate;
    private LocalDateTime timestamp;

    public HistoricalRateWithTimestamp(String rate, LocalDateTime timestamp) {
        this.rate = rate;
        this.timestamp = timestamp;
    }
}
