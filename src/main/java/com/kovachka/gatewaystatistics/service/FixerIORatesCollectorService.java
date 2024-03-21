package com.kovachka.gatewaystatistics.service;

import com.kovachka.gatewaystatistics.config.AppConfig;
import com.kovachka.gatewaystatistics.error.StatisticsCollectorException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.RestClientException;

import java.io.IOException;

@Slf4j
@Service
public class FixerIORatesCollectorService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final AppConfig appConfig;
    private final FixerIOProcessService fixerIOProcessService;

    @Autowired
    public FixerIORatesCollectorService(AppConfig appConfig, FixerIOProcessService fixerIOProcessService) {
        this.appConfig = appConfig;
        this.fixerIOProcessService = fixerIOProcessService;
    }

    @Scheduled(fixedRateString = "${fixer.api.fetch.interval}")
    public void fetchAndPrintRates() throws Exception {
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(appConfig.getFixerApiFullUrl(), String.class);
            log.info("Fixer.io regular updates: {}", response.getBody());
            fixerIOProcessService.processFixerResponse(response.getBody());
        } catch (RestClientException e) {
            log.error("Exception occurred while fetching updates from Fixer.io: {}", e.getMessage());
            throw new StatisticsCollectorException("Something failed in rates updating");
        } catch (IOException e) {
            throw new StatisticsCollectorException(e.getMessage());
        }
    }
}