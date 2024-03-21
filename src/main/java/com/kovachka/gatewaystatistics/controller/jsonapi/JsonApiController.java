package com.kovachka.gatewaystatistics.controller.jsonapi;

import com.kovachka.gatewaystatistics.dto.CurrentRateRequestDto;
import com.kovachka.gatewaystatistics.dto.CurrentRateResponseDto;
import com.kovachka.gatewaystatistics.dto.HistoricalRateRequestDto;
import com.kovachka.gatewaystatistics.dto.HistoricalRateResponseDto;
import com.kovachka.gatewaystatistics.facade.JsonApiFacade;
import com.kovachka.gatewaystatistics.service.UniqueRequestIdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/json_api")
public class JsonApiController {

    private final JsonApiFacade jsonApiFacade;
    private final UniqueRequestIdService uniqueRequestIdService;

    @Autowired
    public JsonApiController(JsonApiFacade jsonApiFacade, UniqueRequestIdService uniqueRequestIdService) {
        this.jsonApiFacade = jsonApiFacade;
        this.uniqueRequestIdService = uniqueRequestIdService;
    }

    @PostMapping(path = "/current", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getCurrentRate(@RequestBody CurrentRateRequestDto rateRequest) {
        if (uniqueRequestIdService.isDuplicateRequest(rateRequest.getRequestId())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Duplicate request detected.");
        }

        Optional<CurrentRateResponseDto> rateResponse = jsonApiFacade.fetchLatestRateForJsonApi(rateRequest);

        return rateResponse.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping(path = "/history", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getHistoricalRates(@RequestBody HistoricalRateRequestDto historicalRequest) {
        if (uniqueRequestIdService.isDuplicateRequest(historicalRequest.getRequestId())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Duplicate request detected.");
        }

        HistoricalRateResponseDto ratesUpdate = jsonApiFacade.fetchHistoricalRatesForJsonApi(historicalRequest);
        if (ratesUpdate != null) {
            return ResponseEntity.ok(ratesUpdate);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}


