package com.kovachka.gatewaystatistics.facade;

import com.kovachka.gatewaystatistics.coordinator.StatisticsCoordinator;
import com.kovachka.gatewaystatistics.dto.*;
import com.kovachka.gatewaystatistics.entity.ExtServiceName;
import com.kovachka.gatewaystatistics.entity.StatisticVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Component
public class XmlApiFacade {

    private final StatisticsCoordinator statisticsCoordinator;

    @Autowired
    public XmlApiFacade(StatisticsCoordinator statisticsCoordinator) {
        this.statisticsCoordinator = statisticsCoordinator;
    }

    public CommandXmlResponseDto fetchLatestRateForXmlApi(CommandXmlRequestDto commandRequest) {
        LocalDateTime time = LocalDateTime.now(ZoneId.of("UTC"));
        StatisticVo statisticVo = new StatisticVo(commandRequest.getId(), ExtServiceName.EXT_SERVICE_1, time, commandRequest.getGet().getConsumer());
        Optional<CurrentRateResponseDto> currentRateResponseDto = statisticsCoordinator.fetchLatestRate(statisticVo, commandRequest.getGet().getCurrency());

        CommandXmlResponseDto response = new CommandXmlResponseDto();
        response.setCurrency(currentRateResponseDto.get().getCurrency());
        response.setRates(List.of(currentRateResponseDto.get().getRate()));

        return response;
    }

    public CommandXmlResponseDto fetchHistoricalRatesForXmlApi(CommandXmlRequestDto commandRequest) {
        LocalDateTime time = LocalDateTime.now(ZoneId.of("UTC"));
        StatisticVo statisticVo = new StatisticVo(commandRequest.getId(), ExtServiceName.EXT_SERVICE_1, time, commandRequest.getHistory().getConsumer());
        HistoricalRateResponseDto historicalRateResponseDto = statisticsCoordinator.fetchHistoricalRates(statisticVo,
                                                                                                         commandRequest.getHistory().getCurrency(),
                                                                                                         Integer.valueOf(commandRequest.getHistory().getPeriod()));

        CommandXmlResponseDto response = new CommandXmlResponseDto();
        response.setCurrency(historicalRateResponseDto.getCurrency());
        response.setRates(historicalRateResponseDto.getUpdates().stream().map(HistoricalRateWithTimestamp::getRate).toList());

        return response;
    }
}
