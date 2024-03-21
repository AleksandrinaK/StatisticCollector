package com.kovachka.gatewaystatistics.facade;

import com.kovachka.gatewaystatistics.coordinator.StatisticsCoordinator;
import com.kovachka.gatewaystatistics.dto.*;
import com.kovachka.gatewaystatistics.entity.ExtServiceName;
import com.kovachka.gatewaystatistics.entity.StatisticVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

@Component
public class JsonApiFacade {

    private final StatisticsCoordinator statisticsCoordinator;

    @Autowired
    public JsonApiFacade(StatisticsCoordinator statisticsCoordinator) {
        this.statisticsCoordinator = statisticsCoordinator;
    }

    public Optional<CurrentRateResponseDto> fetchLatestRateForJsonApi(CurrentRateRequestDto rateRequest) {
        LocalDateTime time = LocalDateTime.ofInstant(Instant.ofEpochSecond(rateRequest.getTimestamp()), ZoneId.of("UTC"));
        StatisticVo statisticVo = new StatisticVo(rateRequest.getRequestId(), ExtServiceName.EXT_SERVICE_2, time, rateRequest.getClient());
        return statisticsCoordinator.fetchLatestRate(statisticVo, rateRequest.getCurrency());
    }

    public HistoricalRateResponseDto fetchHistoricalRatesForJsonApi(HistoricalRateRequestDto historicalRequest) {
        LocalDateTime time = LocalDateTime.ofInstant(Instant.ofEpochSecond(historicalRequest.getTimestamp()), ZoneId.of("UTC"));
        StatisticVo statisticVo = new StatisticVo(historicalRequest.getRequestId(), ExtServiceName.EXT_SERVICE_2, time, historicalRequest.getClient());
        return statisticsCoordinator.fetchHistoricalRates(statisticVo, historicalRequest.getCurrency(), historicalRequest.getPeriod());
    }
}
