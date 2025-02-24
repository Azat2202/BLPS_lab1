package ru.itmo.lab.dto.responses;

import lombok.Data;
import ru.itmo.lab.models.Room;
import ru.itmo.lab.models.User;
import ru.itmo.lab.models.enums.BookingStatus;

import java.time.LocalDate;

@Data
public class BookingResponseDTO {
	private Long id;
	private User user;
	private Room room;
	private BookingStatus status;
	private LocalDate startDate;
	private LocalDate endDate;
}
