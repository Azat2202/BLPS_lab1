package ru.itmo.lab.workers;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.client.ExternalTaskClient;
import org.camunda.bpm.client.task.ExternalTask;

import static org.camunda.spin.Spin.JSON;

import org.camunda.bpm.client.task.ExternalTaskService;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.value.ObjectValue;
import org.camunda.spin.Spin;
import org.camunda.spin.json.SpinJsonNode;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;
import ru.itmo.lab.dto.responses.RoomResponseDTO;
import ru.itmo.lab.models.enums.City;
import ru.itmo.lab.services.SearcherService;

import java.time.ZoneId;
import java.util.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class SearchHotelWorker {

    private final ExternalTaskClient client;
    private final SearcherService searcherService;

    @PostConstruct
    public void subscribe() {
        client.subscribe("search-hotels")
                .lockDuration(1000)
                .handler(this::execute)
                .open();
    }

    @SneakyThrows
    private void execute(ExternalTask externalTask, ExternalTaskService externalTaskService) {
        String hotelName = externalTask.getVariable("hotel_name");
        City city = City.valueOf(externalTask.getVariable("city"));
        Date checkInDate = externalTask.getVariable("checkin_date");
        Date checkoutDate = externalTask.getVariable("checkout_date");
        Long peopleCount = externalTask.getVariable("people_count");
        Long minRating = externalTask.getVariable("min_rating");
        Long maxRating = externalTask.getVariable("max_rating");
        Long minPrice = externalTask.getVariable("min_price");
        Long maxPrice = externalTask.getVariable("max_price");
        log.info("Searching for hotels with: hotelName = {} " +
                "checkinDate = {} " +
                "checkoutDate = {} " +
                "peopleCount = {} " +
                "minRating = {} " +
                "maxRating = {} " +
                "minPrice = {} " +
                "maxPrice = {}", hotelName, checkInDate, checkoutDate, peopleCount, minRating, maxRating, minPrice, maxPrice);

        List<RoomResponseDTO> roomResponseDTOS = searcherService.searchHotel(
                Optional.ofNullable(hotelName),
                checkInDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                checkoutDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                peopleCount.intValue(),
                city,
                Optional.ofNullable(minRating).map(Long::intValue),
                Optional.ofNullable(maxRating).map(Long::intValue),
                Optional.ofNullable(minPrice).map(Long::intValue),
                Optional.ofNullable(maxPrice).map(Long::intValue)
        );
        log.info("found hotels = {}", roomResponseDTOS);

        String hotelNames = roomResponseDTOS.stream()
                .map(roomResponseDTO ->
                        "[ID: " +
                                roomResponseDTO.getId() +
                                " , Отель: " +
                                roomResponseDTO.getHotel().getName() +
                                " , Комната: " +
                                roomResponseDTO.getName() +
                                ", Цена:" +
                                roomResponseDTO.getPrice() +
                                "Р],\n"
                )
                .reduce("", String::concat);

        externalTaskService.complete(externalTask,
                Variables.createVariables()
                        .putValue(
                                "hotel_names",
                                hotelNames
                        )
        );
    }
}
