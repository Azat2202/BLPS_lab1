package ru.itmo.booking_service.workers;

import jakarta.annotation.PostConstruct;
import org.camunda.bpm.client.ExternalTaskClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.itmo.booking_service.models.Booking;
import ru.itmo.booking_service.services.BookingService;

import java.util.Map;

@Component
public class GetBookingByIdWorker {

    @Autowired
    private ExternalTaskClient client;

    @Autowired
    private BookingService bookingService;

    @PostConstruct
    public void subscribe() {
        client.subscribe("get-booking-by-id")
            .lockDuration(1000)
            .handler((externalTask, externalTaskService) -> {
                String bookingId = externalTask.getVariable("bookingId");
                
                externalTaskService.complete(externalTask, Map.of(
                        "bookingId", bookingId
                ));
            })
            .open();
    }
}
