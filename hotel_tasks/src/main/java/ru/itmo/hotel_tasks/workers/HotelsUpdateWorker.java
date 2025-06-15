package ru.itmo.hotel_tasks.workers;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.client.ExternalTaskClient;
import org.camunda.bpm.client.task.ExternalTask;
import org.camunda.bpm.client.task.ExternalTaskService;
import org.camunda.bpm.engine.variable.Variables;
import org.springframework.stereotype.Component;
import ru.itmo.hotel_tasks.services.HotelTasksService;

@Component
@RequiredArgsConstructor
@Slf4j
public class HotelsUpdateWorker {
    private final ExternalTaskClient client;
    private final HotelTasksService hotelTasksService;

    @PostConstruct
    public void subscribe() {
        client.subscribe("hotels-update")
                .lockDuration(1000)
                .handler(this::execute)
                .open();
    }

    private void execute(ExternalTask externalTask, ExternalTaskService externalTaskService) {
        log.info("Updating hotels");

        try {
            hotelTasksService.generateNewHotels();
        } catch (Exception e){
            log.error("Error updating hotels", e);
        }

        externalTaskService.complete(externalTask);
    }
}
