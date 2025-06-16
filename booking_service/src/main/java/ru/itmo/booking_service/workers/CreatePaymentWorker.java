package ru.itmo.booking_service.workers;

import jakarta.annotation.PostConstruct;
import org.camunda.bpm.client.ExternalTaskClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.itmo.booking_service.models.Booking;
import ru.itmo.booking_service.services.BookingService;
import ru.itmo.booking_service.services.PaymentService;

import java.util.Map;

@Component
public class CreatePaymentWorker {

    @Autowired
    private ExternalTaskClient client;

    @Autowired
    private PaymentService paymentService;
	@Autowired
	private BookingService bookingService;
    
    @PostConstruct
    public void subscribe() {
        client.subscribe("create-payment")
            .lockDuration(1000)
            .handler((externalTask, externalTaskService) -> {
                String bookingId = externalTask.getVariable("bookingId");
                Booking booking = bookingService.getBookingById(bookingId);
                Long paymentId = paymentService.createPayment(booking);
                externalTaskService.complete(externalTask, Map.of("paymentId", paymentId, "bookingId", bookingId));
            })
            .open();
    }
}
