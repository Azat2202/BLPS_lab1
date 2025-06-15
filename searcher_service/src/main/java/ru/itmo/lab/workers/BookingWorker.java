package ru.itmo.lab.workers;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.client.ExternalTaskClient;
import org.camunda.bpm.client.task.ExternalTask;
import org.camunda.bpm.client.task.ExternalTaskService;
import org.camunda.bpm.engine.variable.Variables;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.itmo.lab.dto.requests.BookingRequestDTO;
import ru.itmo.lab.dto.responses.RoomResponseDTO;
import ru.itmo.lab.services.BookingService;

import java.time.ZoneId;
import java.util.Date;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class BookingWorker {
    private final ExternalTaskClient client;
    private final BookingService bookingService;
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${camunda.engine-rest}")
    private String camundaUrl;

    @PostConstruct
    public void subscribe() {
        client.subscribe("create-booking")
                .lockDuration(1000)
                .handler(this::execute)
                .open();
    }

    private void execute(ExternalTask externalTask, ExternalTaskService externalTaskService) {
        String userLogin = externalTask.getVariable("login");
        Long roomId = externalTask.getVariable("chosen_room_id");
        Date checkInDate = externalTask.getVariable("checkin_date");
        Date checkoutDate = externalTask.getVariable("checkout_date");

        log.info("Creating booking for roomId = {}, checkInDate = {}, checkoutDate = {}", roomId, checkInDate, checkoutDate);

        Long bookingId = bookingService.createBooking(new BookingRequestDTO(
                userLogin,
                roomId,
                checkInDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                checkoutDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
        ));

        log.info("Created booking with id = {}", bookingId);

        externalTaskService.complete(externalTask,
                Variables.createVariables()
                        .putValue(
                                "booking_id",
                                bookingId
                        )
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Map<String, Object> requestBody = Map.of(
                "messageName", "book-message",
                "processInstanceId", externalTask.getProcessInstanceId(),
                "processVariables", Map.of(
                        "booking_id", Map.of(
                                "value", bookingId,
                                "type", "long"
                        )
                )
        );
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(
                camundaUrl + "/message",
                entity,
                String.class
        );

        log.info("Send message to camunda with response = {}", response);
    }
}
