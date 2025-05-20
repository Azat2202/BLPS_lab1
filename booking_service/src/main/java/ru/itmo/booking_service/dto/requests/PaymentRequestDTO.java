package ru.itmo.booking_service.dto.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PaymentRequestDTO {
	@NotNull(message = "Room ID is required")
	private Long id;
	@NotNull(message = "Booking ID is required")
	private Long bookingId;
}
