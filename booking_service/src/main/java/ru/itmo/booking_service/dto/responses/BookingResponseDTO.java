package ru.itmo.booking_service.dto.responses;

import lombok.Data;
import ru.itmo.booking_service.models.enums.BookingStatus;

import java.time.LocalDate;

@Data
public class BookingResponseDTO {
	private Long id;
	private UserResponseDTO user;
	private RoomResponseDTO room;
	private BookingStatus status;
	private LocalDate startDate;
	private LocalDate endDate;
}
