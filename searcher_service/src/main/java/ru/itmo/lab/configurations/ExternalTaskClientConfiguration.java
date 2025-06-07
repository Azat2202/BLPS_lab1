package ru.itmo.lab.configurations;

import org.camunda.bpm.client.ExternalTaskClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExternalTaskClientConfiguration {

    @Value("${camunda.engine-rest}")
    private String engineRestUrl;

    @Bean
    public ExternalTaskClient externalTaskClient() {
        return ExternalTaskClient.create()
                .baseUrl(engineRestUrl)
                .asyncResponseTimeout(10000)
                .build();
    }

}
