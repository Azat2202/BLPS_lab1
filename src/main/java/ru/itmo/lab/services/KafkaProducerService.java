package ru.itmo.lab.services;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {
	
	private final KafkaTemplate<String, String> kafkaTemplate;
	
	public KafkaProducerService(KafkaTemplate<String, String> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}
	
	public void sendMessage(String message, String key, String topic) {
		try {
			System.out.println("Sending message");
			kafkaTemplate.send(topic, key, message);
			System.out.println("Successfully send message");
		} catch (Exception ex) {
			System.out.println("Failed send message");
			throw ex;
		}
	}
}