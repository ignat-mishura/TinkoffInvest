package ru.mishura.tinkoff.service;

import ru.mishura.tinkoff.entity.Candle;
import ru.mishura.tinkoff.entity.CandleInterval;

import java.util.List;

public interface ServiceTinkoff {

    List<Candle> getCandles(String figi, CandleInterval interval);
}
