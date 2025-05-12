package ru.itmo.lab.dto.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ru.itmo.lab.models.enums.City;
import ru.itmo.lab.models.enums.HotelRating;

@Data
public class HotelRequestDTO {
	@NotNull(message = "Room name is required")
	private String name;
	
	private String description;
	
	@NotNull(message = "City is required")
	private City city;
	
	private String address;
	
	private HotelRating rating;
}
