package ru.itmo.lab.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import ru.itmo.lab.dto.midleware.HotelRoom;
import ru.itmo.lab.models.Hotel;
import ru.itmo.lab.models.enums.City;
import ru.itmo.lab.repositories.HotelRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SearcherService {
    private final HotelRepository hotelRepository;

    public List<HotelRoom> searchHotel(
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
        return hotelRepository.searchRoomsAndHotels(
                hotelName.orElse(""),
                city,
                peopleCount,
                checkinDate,
                checkoutDate,
                minRating.orElse(-1),
                maxRating.orElse(6),
                minPrice.orElse(0),
                maxPrice.orElse(Integer.MAX_VALUE));
    }
}
