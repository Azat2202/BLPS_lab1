package ru.itmo.lab.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.itmo.lab.dto.midleware.HotelRoom;
import ru.itmo.lab.dto.responses.HotelResponseDTO;
import ru.itmo.lab.dto.responses.RoomResponseDTO;
import ru.itmo.lab.models.enums.City;
import ru.itmo.lab.services.SearcherService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "Searcher Controller", description = "Основная страница поиска отелей")
public class SearcherController {
    private final SearcherService searcherService;

    @GetMapping("/search")
    @Operation(summary = "Поиск по отелям", description = "Поиск по отелям с фильтрами")
    public List<RoomResponseDTO> search(
            @RequestParam Optional<String> hotelName,
            @RequestParam LocalDate checkinDate,
            @RequestParam LocalDate checkoutDate,
            @RequestParam Integer peopleCount,
            @RequestParam City city,
            @RequestParam Optional<Integer> minRating,
            @RequestParam Optional<Integer> maxRating,
            @RequestParam Optional<Integer> minPrice,
            @RequestParam Optional<Integer> maxPrice
    ) {
        return searcherService.searchHotel(hotelName,
                checkinDate,
                checkoutDate,
                peopleCount,
                city,
                minRating,
                maxRating,
                minPrice,
                maxPrice);
    }

}
