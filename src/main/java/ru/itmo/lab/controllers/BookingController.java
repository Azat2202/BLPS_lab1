package ru.itmo.lab.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itmo.lab.dto.requests.BookingRequestDTO;
import ru.itmo.lab.dto.responses.BookingResponseDTO;
import ru.itmo.lab.services.BookingService;

@RestController
@RequestMapping("/api/booking")
@RequiredArgsConstructor
@Tag(name = "BookingController", description = "Контроллер для управлением заявками на бронирование")
public class BookingController {
	private final BookingService bookingService;

	@PostMapping("/create")
	@Operation(summary = "Создать бронирование", description = "Создаёт новую заявку на бронирование")
	public ResponseEntity<BookingResponseDTO> createBooking(@RequestBody BookingRequestDTO bookingRequestDTO) {
		BookingResponseDTO createdBooking = bookingService.createBooking(bookingRequestDTO);
		return ResponseEntity.ok(createdBooking);
	}
}
