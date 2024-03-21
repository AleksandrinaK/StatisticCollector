package com.kovachka.gatewaystatistics.dto;

import lombok.Data;

@Data
public class CurrentRateResponseDto {

    private String currency;
    private String rate;

    public CurrentRateResponseDto(String currency, String rate) {
        this.currency = currency;
        this.rate = rate;
    }
}