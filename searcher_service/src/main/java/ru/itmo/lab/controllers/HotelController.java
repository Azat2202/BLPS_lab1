package ru.itmo.lab.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itmo.lab.dto.requests.HotelRequestDTO;
import ru.itmo.lab.dto.requests.RoomRequestDTO;
import ru.itmo.lab.dto.responses.HotelResponseDTO;
import ru.itmo.lab.dto.responses.RoomResponseDTO;
import ru.itmo.lab.services.HotelService;
import ru.itmo.lab.services.RoomService;

@RestController
@RequestMapping("/api/hotel")
@RequiredArgsConstructor
@Tag(name = "HotelController", description = "Контроллер для получения информации об отелях")
public class HotelController {
    private final HotelService hotelService;

    @PreAuthorize("hasAuthority('CREATE_HOTEL')")
    @GetMapping("/create")
    @Operation(summary = "Создать новый отель", description = "Создает новый отель. Доступно только администраторам системы")
    public ResponseEntity<?> create(@Valid HotelRequestDTO hotel) {
        try {
	        HotelResponseDTO hotelResponseDTO = hotelService.create(hotel);
            return ResponseEntity.ok(hotelResponseDTO);
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }
}
