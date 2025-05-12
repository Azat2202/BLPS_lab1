package ru.itmo.lab.dto.requests;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class BookingRequestDTO {
	@NotNull(message = "Room ID is required")
	private Long roomId;
	@NotNull(message = "Start date is required")
	@FutureOrPresent(message = "Start date must be in the present or future")
	private LocalDate startDate;
	@NotNull(message = "Start date is required")
	@FutureOrPresent(message = "Start date must be in the present or future")
	private LocalDate endDate;
}
