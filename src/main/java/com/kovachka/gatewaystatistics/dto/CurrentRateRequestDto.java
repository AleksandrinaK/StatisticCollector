package com.kovachka.gatewaystatistics.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CurrentRateRequestDto {

    @JsonProperty("requestId")
    private String requestId;

    @JsonProperty("timestamp")
    private Long timestamp;

    @JsonProperty("client")
    private String client;

    @JsonProperty("currency")
    private String currency;
}
