package com.kovachka.gatewaystatistics.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StatisticVo {
    private String requestId;
    private ExtServiceName extServiceName;
    private LocalDateTime timestamp;
    private String client;

    public StatisticVo(String requestId, ExtServiceName extServiceName, LocalDateTime timestamp, String client) {
        this.requestId = requestId;
        this.extServiceName = extServiceName;
        this.timestamp = timestamp;
        this.client = client;
    }
}
