package com.kovachka.gatewaystatistics.dto;

import lombok.Data;
import java.util.List;

@Data
public class HistoricalRateResponseDto {
    private String currency;
    private List<HistoricalRateWithTimestamp> updates;

    public HistoricalRateResponseDto(String currency, List<HistoricalRateWithTimestamp> rates) {
        this.currency = currency;
        this.updates = rates;
    }
}
