package ru.itmo.booking_service.workers;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.client.ExternalTaskClient;
import org.camunda.bpm.client.task.ExternalTask;
import org.camunda.bpm.client.task.ExternalTaskService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.itmo.booking_service.models.enums.BookingStatus;
import ru.itmo.booking_service.services.BookingService;

@Component
@RequiredArgsConstructor
@Slf4j
public class BookingSearchWorker {
    private final ExternalTaskClient client;
    private final BookingService bookingService;

    @PostConstruct
    public void subscribe() {
        client.subscribe("get-book-by-id")
                .lockDuration(1000)
                .handler(this::execute)
                .open();
    }

    private void execute(ExternalTask externalTask, ExternalTaskService externalTaskService) {
        try {
            externalTask.getAllVariables().forEach(log::info);
            Long bookingId = externalTask.getVariable("booking_id");

            log.info("Getting booking by id = {}", bookingId);

            boolean isBookingSuccess = bookingService.checkRoomBooking(bookingId);

            if (isBookingSuccess) {
                bookingService.changeStatus(bookingId, BookingStatus.WAITING_PAYMENT);
                externalTaskService.complete(externalTask);
            } else {
                bookingService.changeStatus(bookingId, BookingStatus.CANCELED);
                externalTaskService.handleBpmnError(
                        externalTask,
                        "Комната занята!"
                );
            }
        } catch (Exception e) {
            externalTaskService.handleBpmnError(
                   externalTask,
                   "Не получилось найти бронирование по id!"
            );
        }
    }
}
