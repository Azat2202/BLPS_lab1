package ru.itmo.lab.services;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.itmo.lab.dto.midleware.HotelRoom;
import ru.itmo.lab.dto.responses.HotelResponseDTO;
import ru.itmo.lab.dto.responses.RoomResponseDTO;
import ru.itmo.lab.models.enums.City;
import ru.itmo.lab.repositories.HotelRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearcherService {
    private final HotelRepository hotelRepository;
    private final ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public List<RoomResponseDTO> searchHotel(
            Optional<String> hotelName,
            LocalDate checkinDate,
            LocalDate checkoutDate,
            Integer peopleCount,
            City city,
            Optional<Integer> minRating,
            Optional<Integer> maxRating,
            Optional<Integer> minPrice,
            Optional<Integer> maxPrice
    ) {
        var hotelRooms = hotelRepository.searchRoomsAndHotels(
                hotelName.orElse(""),
                city,
                peopleCount,
                checkinDate,
                checkoutDate,
                minRating.orElse(-1),
                maxRating.orElse(6),
                minPrice.orElse(0),
                maxPrice.orElse(Integer.MAX_VALUE));
        return hotelRooms
                .stream()
                .map(HotelRoom::getRoom)
                .map(room -> modelMapper.map(room, RoomResponseDTO.class))
                .collect(Collectors.toList());
    }
}
