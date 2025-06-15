package ru.itmo.booking_service.workers;

import jakarta.annotation.PostConstruct;
import org.camunda.bpm.client.ExternalTaskClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.itmo.booking_service.services.BookingService;

import java.util.Map;

@Component
public class CheckRoomWorker {

    @Autowired
    private ExternalTaskClient client;

    @Autowired
    private BookingService bookingService;

    @PostConstruct
    public void subscribe() {
        client.subscribe("check-room")
            .lockDuration(1000)
            .handler((externalTask, externalTaskService) -> {
                String bookingId = externalTask.getVariable("bookingId");

                boolean available = bookingService.checkRoomBooking(Long.valueOf(bookingId));
                externalTaskService.complete(externalTask, Map.of("roomAvailable", available));
            })
            .open();
    }
}
