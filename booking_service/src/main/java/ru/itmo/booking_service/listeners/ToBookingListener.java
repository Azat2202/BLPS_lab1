package ru.itmo.booking_service.listeners;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import ru.itmo.booking_service.services.BookingService;
import ru.itmo.lab.kafka.BookingKafkaDTO;

@RequiredArgsConstructor
@Component
public class ToBookingListener {
    private final BookingService bookingService;

    @KafkaListener(topics = "${spring.kafka.consumer.topic-name}")
    public void listenWithHeaders(@Payload BookingKafkaDTO message) {
        bookingService.createBooking(message);
    }
}
