package ru.itmo.lab.dto.requests;

import lombok.Data;
import ru.itmo.lab.models.Booking;
import ru.itmo.lab.models.User;
import ru.itmo.lab.models.enums.PaymentStatus;

import java.time.LocalDate;

@Data
public class PaymentRequestDTO {
	private Long id;
	private Long bookingId;
}
