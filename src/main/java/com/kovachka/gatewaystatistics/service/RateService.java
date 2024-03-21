package com.kovachka.gatewaystatistics.service;

import com.kovachka.gatewaystatistics.dto.CurrentRateResponseDto;
import com.kovachka.gatewaystatistics.dto.HistoricalRateResponseDto;
import com.kovachka.gatewaystatistics.dto.HistoricalRateWithTimestamp;
import com.kovachka.gatewaystatistics.repository.RateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RateService {

    private final RateRepository rateRepository;

    @Autowired
    public RateService(RateRepository rateRepository) {
        this.rateRepository = rateRepository;
    }

    public Optional<CurrentRateResponseDto> getLatestRateForCurrency(String currencyCode) {
        return rateRepository.findTopByCurrencyCurrencyCodeOrderByTimestampDesc(currencyCode)
                             .map(rate -> new CurrentRateResponseDto(rate.getCurrency().getCurrencyCode(), rate.getRate()));
    }

    public HistoricalRateResponseDto getRateHistory(String currencyCode, Integer hours) {
        LocalDateTime endDateTime = LocalDateTime.now();
        LocalDateTime startDateTime = endDateTime.minusHours(hours);

        List<HistoricalRateWithTimestamp> updates = rateRepository.findByCurrencyCurrencyCodeAndTimestampBetween(currencyCode, startDateTime, endDateTime)
                                                                  .stream()
                                                                  .map(rates -> new HistoricalRateWithTimestamp(rates.getRate(), rates.getTimestamp()))
                                                                  .toList();

        return new HistoricalRateResponseDto(currencyCode, updates);
    }
}
