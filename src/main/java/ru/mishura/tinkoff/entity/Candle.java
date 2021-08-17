package ru.mishura.tinkoff.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class Candle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private double o;
    private double c;
    private double h;
    private double l;
    private int v;
    private String time;
    private String interval;
    private String figi;

    @Override
    public String toString() {
        return "Candle{" +
                "id=" + id +
                ", o=" + o +
                ", c=" + c +
                ", h=" + h +
                ", l=" + l +
                ", v=" + v +
                ", time='" + time + '\'' +
                ", interval='" + interval + '\'' +
                ", figi='" + figi + '\'' +
                '}';
    }
}
