package ru.itmo.booking_service.workers;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.client.ExternalTaskClient;
import org.camunda.bpm.client.task.ExternalTask;
import org.camunda.bpm.client.task.ExternalTaskService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.itmo.booking_service.models.Booking;
import ru.itmo.booking_service.repositories.BookingRepository;
import ru.itmo.booking_service.services.BookingService;
import ru.itmo.booking_service.services.PaymentService;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentCreateWorker {
    private final ExternalTaskClient client;
    private final PaymentService paymentService;
    private final BookingRepository bookingRepository;

    @PostConstruct
    public void subscribe() {
        client.subscribe("create-booking")
                .lockDuration(1000)
                .handler(this::execute)
                .open();
    }

    private void execute(ExternalTask externalTask, ExternalTaskService externalTaskService) {
        Long bookingId = externalTask.getVariable("booking_id");

        log.info("Creating payment for bookingId = {}", bookingId);

        paymentService.createPayment(bookingId);

        externalTaskService.complete(externalTask);
    }
}
