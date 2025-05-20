package ru.itmo.booking_service.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.itmo.booking_service.dto.requests.PaymentRequestDTO;
import ru.itmo.booking_service.dto.responses.BookingResponseDTO;
import ru.itmo.booking_service.models.Booking;
import ru.itmo.booking_service.models.enums.BookingStatus;
import ru.itmo.booking_service.services.BookingService;

@RestController
@RequestMapping("/api/booking")
@RequiredArgsConstructor
@Tag(name = "BookingStatusController", description = "Контроллер для управлением статусом заявок на бронирование")
public class BookingStatusController {
	private final BookingService bookingService;
	private final ModelMapper modelMapper;

	@PreAuthorize("hasAuthority('BOOK_ROOM')")
	@PostMapping("/payment_success")
	@Operation(summary = "Подтвердить бронирование", description = "Отправляет подтверждние бронирования на почту")
	public ResponseEntity<?> applyBooking(@Valid @RequestBody PaymentRequestDTO paymentRequestDTO) {
		try {
			Booking booking = bookingService.changeStatus(paymentRequestDTO.getBookingId(), BookingStatus.SUCCESSES);

			return ResponseEntity.ok(modelMapper.map(booking, BookingResponseDTO.class));
		} catch (IllegalArgumentException exception) {
			return ResponseEntity.badRequest().body(exception.getMessage());
		}
	}
	
	@PreAuthorize("hasAuthority('CANCEL_BOOKING')")
	@DeleteMapping("/cancel_booking")
	@Operation(summary = "Отменяет бронирование", description = "Отменяет бронирование. Доступно только администраторам системы")
	public ResponseEntity<?> cancel_booking(@Valid Long bookingId) {
		try {
			Booking booking = bookingService.changeStatus(bookingId, BookingStatus.CANCELED);
			return ResponseEntity.ok(modelMapper.map(booking, BookingResponseDTO.class));
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}
