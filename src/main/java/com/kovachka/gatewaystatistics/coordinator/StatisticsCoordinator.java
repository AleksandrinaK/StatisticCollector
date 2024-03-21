package com.kovachka.gatewaystatistics.coordinator;

import com.kovachka.gatewaystatistics.dto.*;
import com.kovachka.gatewaystatistics.entity.StatisticVo;
import com.kovachka.gatewaystatistics.service.RateService;
import com.kovachka.gatewaystatistics.service.StatisticMessagePublisher;
import com.kovachka.gatewaystatistics.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class StatisticsCoordinator {

    private final RateService rateService;
    private final StatisticService statisticService;
    private final StatisticMessagePublisher statisticMessagePublisher;

    @Autowired
    public StatisticsCoordinator(RateService rateService, StatisticService statisticService, StatisticMessagePublisher statisticMessagePublisher) {
        this.rateService = rateService;
        this.statisticService = statisticService;
        this.statisticMessagePublisher = statisticMessagePublisher;
    }

    public Optional<CurrentRateResponseDto> fetchLatestRate(StatisticVo statisticVo, String currency) {
        statisticService.saveStatisticData(statisticVo);
        statisticMessagePublisher.sendStatisticMessage(statisticVo);
        return rateService.getLatestRateForCurrency(currency);
    }

    public HistoricalRateResponseDto fetchHistoricalRates(StatisticVo statisticVo, String currency, Integer period) {
        statisticService.saveStatisticData(statisticVo);
        statisticMessagePublisher.sendStatisticMessage(statisticVo);
        return rateService.getRateHistory(currency, period);
    }
}
