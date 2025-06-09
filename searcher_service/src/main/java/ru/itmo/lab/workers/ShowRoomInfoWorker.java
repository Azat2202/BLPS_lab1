package ru.itmo.lab.workers;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.client.ExternalTaskClient;
import org.camunda.bpm.client.task.ExternalTask;
import org.camunda.bpm.client.task.ExternalTaskService;
import org.camunda.bpm.engine.variable.Variables;
import org.springframework.stereotype.Component;
import ru.itmo.lab.dto.responses.RoomResponseDTO;
import ru.itmo.lab.services.SearcherService;

@Component
@RequiredArgsConstructor
@Slf4j
public class ShowRoomInfoWorker {
    private final ExternalTaskClient client;
    private final SearcherService searcherService;

    @PostConstruct
    public void subscribe() {
        client.subscribe("show-room-info")
                .lockDuration(1000)
                .handler(this::execute)
                .open();
    }

    private void execute(ExternalTask externalTask, ExternalTaskService externalTaskService) {
        Long roomId = externalTask.getVariable("chosen_room_id");
        log.info("Showing room info for id = {}", roomId);

        RoomResponseDTO roomResponseDTO = searcherService.searchRoom(roomId);

        log.info("Found room: {}", roomResponseDTO);

        String roomResponse =
                "Имя: " +
                        roomResponseDTO.getName() +
                        ", Цена: " +
                        roomResponseDTO.getPrice() +
                        "Р, Вместительность: " +
                        roomResponseDTO.getCapacity() +
                        ", Название отеля: " +
                        roomResponseDTO.getHotel().getName();

        externalTaskService.complete(externalTask,
                Variables.createVariables()
                        .putValue(
                                "room_info",
                                roomResponse
                        )
        );
    }

}
