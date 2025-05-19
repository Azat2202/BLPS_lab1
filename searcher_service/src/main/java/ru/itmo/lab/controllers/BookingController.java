package ru.itmo.lab.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.itmo.lab.dto.requests.BookingRequestDTO;
import ru.itmo.lab.dto.responses.BookingResponseDTO;
import ru.itmo.lab.dto.responses.BookingStatusResponseDTO;
import ru.itmo.lab.kafka.BookingKafkaDTO;
import ru.itmo.lab.models.enums.BookingStatus;
import ru.itmo.lab.services.BookingService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/booking")
@RequiredArgsConstructor
@Tag(name = "BookingController", description = "Контроллер для управлением заявками на бронирование")
public class BookingController {
	private final BookingService bookingService;
	
	@PreAuthorize("hasAuthority('BOOK_ROOM')")
	@PostMapping("/create")
	@Operation(summary = "Создать бронирование", description = "Создаёт новую заявку на бронирование")
	public ResponseEntity<?> createBooking(@Valid @RequestBody BookingRequestDTO bookingRequestDTO) {
		try {
			Long createdBookingId = bookingService.createBooking(bookingRequestDTO);
			String answer = "Заявка создана, ожидайте подтверждения.";
			BookingResponseDTO responseDTO = new BookingResponseDTO(createdBookingId, answer);
			return ResponseEntity.ok(responseDTO);
		} catch (IllegalArgumentException exception) {
			return ResponseEntity.badRequest().body(exception.getMessage());
		}
	}
	
	@PreAuthorize("hasAuthority('BOOK_ROOM')")
	@GetMapping("/booking_status/{bookingId}")
	@Operation(summary = "Посмотреть статус бронирования", description = "Вовращает статус бронирования по его Id")
	public ResponseEntity<?> checkBookingStatus(@Valid @PathVariable Long bookingId) {
		try {
			BookingStatus status = bookingService.checkBookingStatus(bookingId);
			BookingStatusResponseDTO responseDTO = new BookingStatusResponseDTO(bookingId, status);
			return ResponseEntity.ok(responseDTO);
		} catch (IllegalArgumentException exception) {
			return ResponseEntity.badRequest().body(exception.getMessage());
		}
	}
	

	@PreAuthorize("hasAuthority('VIEW_RESERVATIONS')")
	@GetMapping("/actual_reservations")
	@Operation(summary = "Посмотреть бронирования в выбранном отеле на ближайшие даты", description = "Отправляет подтверждние бронирования на почту")
	public ResponseEntity<?> check_bookings(@Valid @RequestParam String hotelName,
	                                        @Valid @RequestParam LocalDate checkinDate,
	                                        @Valid @RequestParam LocalDate checkoutDate) {
		List<BookingResponseDTO> bookings = bookingService.find(hotelName, checkinDate, checkoutDate);
		return ResponseEntity.ok(bookings);
	}
}
