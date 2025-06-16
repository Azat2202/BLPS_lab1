package ru.itmo.booking_service.workers;

import jakarta.annotation.PostConstruct;
import org.camunda.bpm.client.ExternalTaskClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.itmo.booking_service.services.PaymentService;

@Component
public class SetPaymentStatusExpiredWorker {

    @Autowired
    private ExternalTaskClient client;

    @Autowired
    private PaymentService paymentService;

    @PostConstruct
    public void subscribe() {
        client.subscribe("set-payment-status-expired")
            .lockDuration(1000)
            .handler((externalTask, externalTaskService) -> {
                String paymentId = externalTask.getVariable("paymentId");
                paymentService.markAsExpired(paymentId);
                externalTaskService.complete(externalTask);
            })
            .open();
    }
}
