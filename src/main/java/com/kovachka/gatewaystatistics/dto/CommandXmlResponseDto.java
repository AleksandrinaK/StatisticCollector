package com.kovachka.gatewaystatistics.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

@JacksonXmlRootElement(localName = "result")
public class CommandXmlResponseDto {

    private String currency;

    @JacksonXmlElementWrapper(localName = "latest")
    @JacksonXmlProperty(localName = "rate")
    private List<String> rates;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(final String currency) {
        this.currency = currency;
    }

    public List<String> getRates() {
        return rates;
    }

    public void setRates(final List<String> rates) {
        this.rates = rates;
    }
}

