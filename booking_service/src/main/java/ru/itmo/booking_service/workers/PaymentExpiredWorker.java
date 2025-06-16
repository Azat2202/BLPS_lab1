package ru.itmo.booking_service.workers;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.client.ExternalTaskClient;
import org.camunda.bpm.client.task.ExternalTask;
import org.camunda.bpm.client.task.ExternalTaskService;
import org.springframework.stereotype.Component;
import ru.itmo.booking_service.models.enums.BookingStatus;
import ru.itmo.booking_service.repositories.BookingRepository;
import ru.itmo.booking_service.services.BookingService;
import ru.itmo.booking_service.services.PaymentService;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentExpiredWorker {
    private final ExternalTaskClient client;
    private final BookingService bookingService;

    @PostConstruct
    public void subscribe() {
        client.subscribe("status-expired")
                .lockDuration(1000)
                .handler(this::execute)
                .open();
    }

    private void execute(ExternalTask externalTask, ExternalTaskService externalTaskService) {
        Long bookingId = externalTask.getVariable("booking_id");

        log.info("Expiring booking for bookingId = {}", bookingId);

        bookingService.changeStatus(bookingId, BookingStatus.EXPIRED);

        externalTaskService.complete(externalTask);
    }
}
