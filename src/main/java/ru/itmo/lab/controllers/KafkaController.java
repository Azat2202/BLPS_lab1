package ru.itmo.lab.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.itmo.lab.services.KafkaProducerService;

@RestController
@RequestMapping("/kafka")
@RequiredArgsConstructor
@Tag(name = "HotelController", description = "Контроллер для тестирования кафки")
public class KafkaController {

    @Autowired
    private KafkaProducerService kafkaProducerService;

    @PostMapping("/send")
    @Operation(summary = "Отправить сообщение", description = "Отправляет сообщение с ключом в топик")
    public String sendMessage(@RequestParam String message, @RequestParam String key, @RequestParam String topic) {
        kafkaProducerService.sendMessage(message, key, topic);
        return "Message sent to Kafka!";
    }
}