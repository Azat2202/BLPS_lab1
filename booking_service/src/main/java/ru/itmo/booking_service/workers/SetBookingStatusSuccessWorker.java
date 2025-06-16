package ru.itmo.booking_service.workers;

import jakarta.annotation.PostConstruct;
import org.camunda.bpm.client.ExternalTaskClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.itmo.booking_service.models.enums.BookingStatus;
import ru.itmo.booking_service.services.BookingService;

@Component
public class SetBookingStatusSuccessWorker {

    @Autowired
    private ExternalTaskClient client;

    @Autowired
    private BookingService bookingService;

    @PostConstruct
    public void subscribe() {
        client.subscribe("set-booking-status-success")
            .lockDuration(1000)
            .handler((externalTask, externalTaskService) -> {
                String bookingId = externalTask.getVariable("bookingId");
                bookingService.changeStatus(Long.parseLong(bookingId), BookingStatus.SUCCESSES);
                externalTaskService.complete(externalTask);
            })
            .open();
    }
}
