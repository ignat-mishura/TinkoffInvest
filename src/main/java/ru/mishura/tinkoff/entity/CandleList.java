package ru.mishura.tinkoff.entity;

import java.util.ArrayList;
import java.util.List;

public class CandleList {
    private List<Candle> candles;

    public  CandleList(){
        candles = new ArrayList<>();
    }

    public CandleList(List<Candle> candles) {
        this.candles = candles;
    }
}
