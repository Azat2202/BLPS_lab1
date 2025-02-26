package ru.itmo.lab.dto.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ru.itmo.lab.models.Booking;
import ru.itmo.lab.models.User;
import ru.itmo.lab.models.enums.PaymentStatus;

import java.time.LocalDate;

@Data
public class PaymentRequestDTO {
	@NotNull(message = "Room ID is required")
	private Long id;
	@NotNull(message = "Booking ID is required")
	private Long bookingId;
}
