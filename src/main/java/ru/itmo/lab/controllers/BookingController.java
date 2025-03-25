package ru.itmo.lab.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.itmo.lab.dto.requests.BookingRequestDTO;
import ru.itmo.lab.dto.requests.PaymentRequestDTO;
import ru.itmo.lab.dto.responses.BookingResponseDTO;
import ru.itmo.lab.dto.responses.PaymentResponseDTO;
import ru.itmo.lab.models.Booking;
import ru.itmo.lab.models.Payment;
import ru.itmo.lab.services.BookingService;
import ru.itmo.lab.services.PaymentService;
import ru.itmo.lab.services.Scheduler;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/booking")
@RequiredArgsConstructor
@Tag(name = "BookingController", description = "Контроллер для управлением заявками на бронирование")
public class BookingController {
	private final BookingService bookingService;
	private final PaymentService paymentService;
	private final Scheduler scheduler;
	private final ModelMapper modelMapper;
	
	@PreAuthorize("hasAuthority('BOOK_ROOM')")
	@PostMapping("/create")
	@Operation(summary = "Создать бронирование", description = "Создаёт новую заявку на бронирование")
	public ResponseEntity<?> createBooking(@Valid @RequestBody BookingRequestDTO bookingRequestDTO) {
		try {
			BookingResponseDTO createdBooking = bookingService.createBooking(bookingRequestDTO);
			
			boolean isFree = bookingService.checkRoomBooking(createdBooking);
			if (!isFree) {
				throw new IllegalArgumentException("Room is unavailable for the selected period");
			}
			
			PaymentResponseDTO createdPayment = paymentService.createPayment(createdBooking);
			
			Payment payment = modelMapper.map(createdPayment, Payment.class);
			scheduler.schedulePaymentExpiration(payment);
			
			return ResponseEntity.ok(createdPayment);
		} catch (IllegalArgumentException exception) {
			return ResponseEntity.badRequest().body(exception.getMessage());
		}
	}
	
	@PreAuthorize("hasAuthority('BOOK_ROOM')")
	@PostMapping("/payment_success")
	@Operation(summary = "Подтвердить бронирование", description = "Отправляет подтверждние бронирования на почту")
	public ResponseEntity<?> applyBooking(@Valid @RequestBody PaymentRequestDTO paymentRequestDTO) {
		try {
			scheduler.cancelPaymentExpiration(paymentRequestDTO);
			
			BookingResponseDTO bookingResponseDTO = bookingService.doBookingSuccess(paymentRequestDTO);
			
			return ResponseEntity.ok(bookingResponseDTO);
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
	
	@PreAuthorize("hasAuthority('CANCEL_BOOKING')")
	@DeleteMapping("/cancel_booking")
	@Operation(summary = "Отменяет бронирование", description = "Отменяет бронирование. Доступно только администраторам системы")
	public ResponseEntity<?> cancel_booking(@Valid Long bookingId) {
		try {
			BookingResponseDTO booking = bookingService.cancel(bookingId);
			return ResponseEntity.ok(booking);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}
