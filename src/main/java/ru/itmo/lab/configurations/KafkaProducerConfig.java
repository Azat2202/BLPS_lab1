package ru.itmo.lab.configurations;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaProducerConfig {
    
    @Autowired
    private KafkaProperties kafkaProperties;

    @Bean
    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getServer());
        props.put(ProducerConfig.CLIENT_ID_CONFIG, kafkaProperties.getProducerId());
        props.put(
                ProducerConfig.INTERCEPTOR_CLASSES_CONFIG, 
                "com.example.configuration.kafka.KafkaProducerLoggingInterceptor"
        );
        
        if ("SASL_SSL".equals(kafkaProperties.getSecurityProtocol())) {
            props.put("ssl.truststore.location", kafkaProperties.getSslTrustStoreLocation());
            props.put("ssl.truststore.password", kafkaProperties.getSslTrustStorePassword());
            props.put("ssl.truststore.type", kafkaProperties.getSslTrustStoreType());
            props.put("ssl.keystore.type", kafkaProperties.getSslKeyStoreType());
          
            props.put("sasl.mechanism", kafkaProperties.getSaslMechanism());
            props.put("security.protocol", kafkaProperties.getSecurityProtocol());
            props.put("sasl.jaas.config", kafkaProperties.getJaasConfigCompiled());
        }
        
        return props;
    }
  
    @Bean
    public ProducerFactory<String, String> producerFactory() {
        var stringSerializerKey = new StringSerializer();
        stringSerializerKey.configure(Map.of("key.serializer.encoding", "UTF-8"), true);
        stringSerializerKey.configure(Map.of("serializer.encoding", "UTF-8"), true);
    
        var stringSerializerValue = new StringSerializer();
        stringSerializerValue.configure(Map.of("value.serializer.encoding", "UTF-8"), false);
        stringSerializerValue.configure(Map.of("serializer.encoding", "UTF-8"), false);
    
        return new DefaultKafkaProducerFactory<>(producerConfigs(), stringSerializerKey, stringSerializerValue);
    }
  
    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}