package com.kovachka.gatewaystatistics.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
public class AppConfig {

    @Value("${fixer.api.base-url}")
    private String fixerApiBaseUrl;

    @Value("${fixer.api.key}")
    private String fixerApiKey;

    public String getFixerApiFullUrl() {
        return fixerApiBaseUrl + "?access_key=" + fixerApiKey;
    }
}