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
	public ResponseEntity<?> createBooking(@RequestBody BookingRequestDTO bookingRequestDTO) {
		try {
			BookingResponseDTO createdBooking = bookingService.createBooking(bookingRequestDTO);
			boolean isFree = bookingService.checkRoomBooking(createdBooking);
			if (!isFree) {
				throw new IllegalArgumentException("Room is unavailable for the selected period");
			}
			return ResponseEntity.ok(createdBooking);
		} catch (IllegalArgumentException exception) {
			return ResponseEntity.badRequest().body(exception.getMessage());
		}
	}
}
