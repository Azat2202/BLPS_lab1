package ru.itmo.lab.dto.responses;

import lombok.Data;
import ru.itmo.lab.models.enums.BookingStatus;

@Data
public class BookingResponseDTO {
	private Long id;
	private String username;
	private Long roomId;
	private BookingStatus status;
	private String date;
}
