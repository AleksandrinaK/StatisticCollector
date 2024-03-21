package com.kovachka.gatewaystatistics.repository;

import com.kovachka.gatewaystatistics.model.Currency;
import com.kovachka.gatewaystatistics.model.Rate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface RateRepository extends JpaRepository<Rate, Long> {
    Optional<Rate> findTopByCurrencyOrderByTimestampDesc(Currency currency);

    Optional<Rate> findTopByCurrencyCurrencyCodeOrderByTimestampDesc(String currencyCode);

    List<Rate> findByCurrencyCurrencyCodeAndTimestampBetween(String currencyCode,
                                                             LocalDateTime startDateTime,
                                                             LocalDateTime endDateTime);
}
