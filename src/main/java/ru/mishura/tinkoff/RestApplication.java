package ru.mishura.tinkoff;

import java.util.*;

import org.hibernate.Session;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.mishura.tinkoff.entity.Candle;
import ru.mishura.tinkoff.entity.CandleInterval;
import ru.mishura.tinkoff.service.ServiceTinkoff;
import ru.mishura.tinkoff.service.ServiceTinkoffRest;


public class RestApplication {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        ServiceTinkoff serviceTinkoff = context.getBean("service", ServiceTinkoffRest.class);

        Session session = HibernateUtil.getSessionFactory().openSession();

        List<Candle> candleList = serviceTinkoff.getCandles("BBG000BL9C59", CandleInterval.FIVE_MINUTES);
        for (Candle candle : candleList) {
            session.beginTransaction();
            session.save(candle);
            session.getTransaction().commit();
        }
    }
}
