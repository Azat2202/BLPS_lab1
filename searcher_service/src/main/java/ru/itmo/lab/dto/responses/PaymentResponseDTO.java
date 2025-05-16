package ru.itmo.lab.dto.responses;

import lombok.Data;
import ru.itmo.lab.models.Booking;
import ru.itmo.lab.models.Room;
import ru.itmo.lab.models.User;
import ru.itmo.lab.models.enums.BookingStatus;
import ru.itmo.lab.models.enums.PaymentStatus;

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
