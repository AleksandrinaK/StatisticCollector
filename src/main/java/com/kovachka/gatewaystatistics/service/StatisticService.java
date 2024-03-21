package com.kovachka.gatewaystatistics.service;

import com.kovachka.gatewaystatistics.entity.StatisticVo;
import com.kovachka.gatewaystatistics.model.Statistic;
import com.kovachka.gatewaystatistics.repository.StatisticRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class StatisticService {

    private final StatisticRepository statisticRepository;

    @Autowired
    public StatisticService(StatisticRepository statisticRepository) {
        this.statisticRepository = statisticRepository;
    }

    public void saveStatisticData(StatisticVo statisticVo) {
        log.info("Save into DB statistic data");
        Statistic statistic = new Statistic(statisticVo);
        statisticRepository.save(statistic);
    }
}

