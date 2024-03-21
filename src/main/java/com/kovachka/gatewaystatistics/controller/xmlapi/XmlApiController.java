package com.kovachka.gatewaystatistics.controller.xmlapi;

import com.kovachka.gatewaystatistics.dto.CommandXmlRequestDto;
import com.kovachka.gatewaystatistics.facade.XmlApiFacade;
import com.kovachka.gatewaystatistics.service.UniqueRequestIdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class XmlApiController {

    private final XmlApiFacade xmlApiFacade;
    private final UniqueRequestIdService uniqueRequestIdService;

    @Autowired
    public XmlApiController(XmlApiFacade xmlApiFacade, final UniqueRequestIdService uniqueRequestIdService) {
        this.xmlApiFacade = xmlApiFacade;
        this.uniqueRequestIdService = uniqueRequestIdService;
    }

    @PostMapping(value = "/xml_api/command", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<?> processXmlCommand(@RequestBody CommandXmlRequestDto commandRequest) {
        if (uniqueRequestIdService.isDuplicateRequest(commandRequest.getId())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Duplicate request detected.");
        }

        if (commandRequest.getGet() != null) {
            return ResponseEntity.ok(xmlApiFacade.fetchLatestRateForXmlApi(commandRequest));
        } else if (commandRequest.getHistory() != null) {
            return ResponseEntity.ok(xmlApiFacade.fetchHistoricalRatesForXmlApi(commandRequest));
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }
}


