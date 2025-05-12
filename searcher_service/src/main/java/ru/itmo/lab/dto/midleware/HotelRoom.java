package ru.itmo.lab.dto.midleware;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.itmo.lab.models.Hotel;
import ru.itmo.lab.models.Room;

@Data
@AllArgsConstructor
public class HotelRoom {
    private Hotel hotel;
    private Room room;
}
