package ru.itmo.hotel_tasks.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.itmo.hotel_tasks.jca.YandexMapsConnection;
import ru.itmo.hotel_tasks.jca.YandexMapsConnectionFactory;
import ru.itmo.hotel_tasks.jca.YandexMapsConnectionImpl;

@Configuration
public class JcaConfiguration {

    @Value("${maps.api-key}")
    private String apiKey;

    @Value("${yandexmaps.api-key}")
    private String yandexMapsApiKey;



    @Bean
    public YandexMapsConnection yandexMapsConnection() {
        return new YandexMapsConnectionImpl(null, null, apiKey, yandexMapsApiKey);
    }
}
