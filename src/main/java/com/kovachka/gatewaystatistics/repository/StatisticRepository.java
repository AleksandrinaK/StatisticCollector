package com.kovachka.gatewaystatistics.repository;

import com.kovachka.gatewaystatistics.model.Statistic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatisticRepository extends JpaRepository<Statistic, String> {
}
