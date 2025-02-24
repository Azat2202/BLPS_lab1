package ru.itmo.lab.dto.requests;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BookingRequestDTO {
	private Long roomId;
	private LocalDate date;
}
