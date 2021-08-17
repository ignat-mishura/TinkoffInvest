package ru.mishura.tinkoff.entity;

public enum CandleInterval {
    ONE_MINUTE("1min"),
    FIVE_MINUTES("5min");
    private String value;
    CandleInterval(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}