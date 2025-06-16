package ru.itmo.booking_service.workers;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.client.ExternalTaskClient;
import org.camunda.bpm.client.task.ExternalTask;
import org.camunda.bpm.client.task.ExternalTaskService;
import org.camunda.bpm.engine.variable.Variables;
import org.springframework.stereotype.Component;
import ru.itmo.booking_service.models.Booking;
import ru.itmo.booking_service.models.enums.BookingStatus;
import ru.itmo.booking_service.services.BookingService;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentSuccessWorker {
    private final ExternalTaskClient client;
    private final BookingService bookingService;

    @PostConstruct
    public void subscribe() {
        client.subscribe("status-success")
                .lockDuration(1000)
                .handler(this::execute)
                .open();
    }

    private void execute(ExternalTask externalTask, ExternalTaskService externalTaskService) {
        Long bookingId = externalTask.getVariable("booking_id");

        log.info("Success payment for bookingId = {}", bookingId);

        Booking booking = bookingService.changeStatus(bookingId, BookingStatus.SUCCESSES);

        String message = "Название отеля: " + booking.getRoom().getHotel().getName() +
                ", Название комнаты: " + booking.getRoom().getName() +
                ", Адрес: " + booking.getRoom().getHotel().getAddress();

        externalTaskService.complete(externalTask,
                Variables.createVariables()
                        .putValue("booking_info", message));
    }
}
