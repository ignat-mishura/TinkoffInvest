package ru.mishura.tinkoff.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import ru.mishura.tinkoff.entity.Candle;
import ru.mishura.tinkoff.entity.CandleInterval;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.*;


public class ServiceTinkoffRest implements ServiceTinkoff{

    private final RestTemplate restTemplate;
    private final String serviceUrl;
    private final HttpHeaders headers;
    private String dateFrom;
    private String dateTo;

    public ServiceTinkoffRest(RestTemplate restTemplate, String token, String url) {
        this.restTemplate = restTemplate;
        this.serviceUrl = url;
        HttpHeaders newHeader = new HttpHeaders();
        newHeader.add("Authorization", "Bearer " + token);
        this.headers = newHeader;
    }

    public List<Candle> getCandles(String figi, CandleInterval interval){
        setProperFormDates(interval);
        URIBuilder builder;
        URI uri;
        try {
            builder = new URIBuilder(serviceUrl + "/market/candles");
            builder.addParameter("figi", figi);
            builder.addParameter("from", dateFrom);
            builder.addParameter("to", dateTo);
            builder.addParameter("interval", interval.getValue());
            uri = builder.build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
        ObjectMapper mapper = new ObjectMapper();
        try {
            ArrayNode arrayNode = (ArrayNode) mapper.readTree(responseEntity.getBody()).get("payload").get("candles");
            List<Candle> candles = new ArrayList<>();
            Iterator<JsonNode> iterator = arrayNode.elements();
            while (iterator.hasNext())
                candles.add(mapper.treeToValue(iterator.next(), Candle.class));

            return candles;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    private void setProperFormDates(CandleInterval interval) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.HOUR, -6);
        this.dateTo = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.GERMANY)
                .format(calendar.getTime());
        switch (interval.getValue()) {
            case "1min":
                calendar.add(Calendar.MINUTE, -1);
            case "5min":
                calendar.add(Calendar.MINUTE, -5);
            default:
                calendar.add(Calendar.MINUTE, -1);
        }
        this.dateFrom = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.GERMANY)
                .format(calendar.getTime());
    }

}
