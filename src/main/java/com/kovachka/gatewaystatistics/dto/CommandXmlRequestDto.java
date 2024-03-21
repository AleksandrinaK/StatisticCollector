package com.kovachka.gatewaystatistics.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "command")
public class CommandXmlRequestDto {

    @JacksonXmlProperty(isAttribute = true, localName = "id")
    private String id;
    private Get get;
    private History history;

    public static class Get {
        private String consumer;
        private String currency;

        public String getConsumer() {
            return consumer;
        }

        public String getCurrency() {
            return currency;
        }
    }

    public static class History {
        @JacksonXmlProperty(isAttribute = true)
        private String consumer;

        @JacksonXmlProperty(isAttribute = true)
        private String currency;

        @JacksonXmlProperty(isAttribute = true)
        private String period;

        public String getConsumer() {
            return consumer;
        }

        public String getCurrency() {
            return currency;
        }

        public String getPeriod() {
            return period;
        }
    }

    public String getId() {
        return id;
    }

    public Get getGet() {
        return get;
    }

    public History getHistory() {
        return history;
    }
}