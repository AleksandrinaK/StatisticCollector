package com.kovachka.gatewaystatistics.model;

import com.kovachka.gatewaystatistics.entity.ExtServiceName;
import com.kovachka.gatewaystatistics.entity.StatisticVo;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "statistics")
public class Statistic {

    @Id
    private String requestId;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ExtServiceName extServiceName;
    @Column(nullable = false)
    private LocalDateTime time;
    @Column(nullable = false)
    private String clientId;

    public Statistic() {
    }

    public Statistic(StatisticVo statisticVo) {
        this.requestId = statisticVo.getRequestId();
        this.extServiceName = statisticVo.getExtServiceName();
        this.time = statisticVo.getTimestamp();
        this.clientId = statisticVo.getClient();
    }
}

