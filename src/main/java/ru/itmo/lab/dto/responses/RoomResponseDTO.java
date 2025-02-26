package ru.itmo.lab.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomResponseDTO {
    private Long id;

    private String name;

    private Long capacity;

    private Integer price;

    private HotelResponseDTO hotel;
}

