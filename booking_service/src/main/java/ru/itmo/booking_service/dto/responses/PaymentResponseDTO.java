package ru.itmo.booking_service.dto.responses;

import lombok.Data;
import ru.itmo.booking_service.models.User;
import ru.itmo.booking_service.models.enums.PaymentStatus;

import java.time.LocalDate;

@Data
public class PaymentResponseDTO {
	private Long id;
	private User user;
	private Integer amount;
	private PaymentStatus status;
	private LocalDate date;
	private BookingResponseDTO booking;
}
