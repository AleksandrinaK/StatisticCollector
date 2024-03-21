package com.kovachka.gatewaystatistics.service;

import com.kovachka.gatewaystatistics.config.RabbitMQConfig;
import com.kovachka.gatewaystatistics.entity.StatisticVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class StatisticMessagePublisher {

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public StatisticMessagePublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendStatisticMessage(StatisticVo statistic) {
        String message = convertStatisticToMessage(statistic);
        log.info("Publish statistic message: {}", message);
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY, message);
    }

    private String convertStatisticToMessage(StatisticVo statistic) {
        return "{\"serviceName\": \"" + statistic.getExtServiceName() + "\", \"requestId\": \"" + statistic.getRequestId() + "\", \"time\": \"" + statistic.getTimestamp() + "\", \"clientId\": \"" + statistic.getClient() + "\"}";
    }
}

