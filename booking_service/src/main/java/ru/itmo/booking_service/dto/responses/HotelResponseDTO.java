package ru.itmo.booking_service.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itmo.booking_service.models.enums.City;
import ru.itmo.booking_service.models.enums.HotelRating;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HotelResponseDTO {
    private Long id;

    private String name;

    private String description;

    private City city;

    private String address;

    private HotelRating rating;
}
