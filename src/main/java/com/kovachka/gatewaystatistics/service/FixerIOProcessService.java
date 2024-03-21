package com.kovachka.gatewaystatistics.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kovachka.gatewaystatistics.entity.FixerIODataVo;
import com.kovachka.gatewaystatistics.error.StatisticsCollectorException;
import com.kovachka.gatewaystatistics.repository.CurrencyRepository;
import com.kovachka.gatewaystatistics.repository.RateRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kovachka.gatewaystatistics.model.*;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

@Slf4j
@Service
public class FixerIOProcessService {

    private final CurrencyRepository currencyRepository;
    private final RateRepository rateRepository;

    @Autowired
    public FixerIOProcessService(CurrencyRepository currencyRepository, RateRepository rateRepository) {
        this.currencyRepository = currencyRepository;
        this.rateRepository = rateRepository;
    }

    public void processFixerResponse(String jsonResponse) throws IOException, StatisticsCollectorException {
        log.info("Process new data from Fixer.io");
        ObjectMapper objectMapper = new ObjectMapper();
        FixerIODataVo fixerResponse = objectMapper.readValue(jsonResponse, FixerIODataVo.class);
        LocalDateTime timestamp = LocalDateTime.ofInstant(Instant.ofEpochSecond(fixerResponse.getTimestamp()), ZoneId.systemDefault());

        if (fixerResponse.getRates() == null) {
            log.error("Problem with the subscription plan");
            throw new StatisticsCollectorException("Subscription limit is reached");
        }

        fixerResponse.getRates().forEach((code, value) -> {
            String rate = String.valueOf(value);
            Currency currency = currencyRepository.findByCurrencyCode(code)
                                                  .orElseGet(() -> currencyRepository.save(new Currency(code)));
            Optional<Rate> latestRate = rateRepository.findTopByCurrencyOrderByTimestampDesc(currency);
            if (latestRate.isEmpty() || !latestRate.get().getRate().equals(rate)) {
                rateRepository.save(new Rate(currency, rate, timestamp, LocalDate.parse(fixerResponse.getDate())));
                log.info("Save into DB for currency {} new update for rate {}", currency.getCurrencyCode(), rate);
            }
        });
    }
}

