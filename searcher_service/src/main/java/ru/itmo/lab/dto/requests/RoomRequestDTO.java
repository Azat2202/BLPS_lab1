package ru.itmo.lab.dto.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ru.itmo.lab.dto.responses.HotelResponseDTO;

@Data
public class RoomRequestDTO {
	@NotNull(message = "Room name is required")
	private String name;
	
	@NotNull(message = "Room capacity is required")
	private Long capacity;
	
	@NotNull(message = "Room price is required")
	private Integer price;
	
	@NotNull(message = "Hotel id is required")
	private Long hotelId;
}
