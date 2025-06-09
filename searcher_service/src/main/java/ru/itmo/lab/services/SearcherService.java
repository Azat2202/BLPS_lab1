package ru.itmo.lab.services;

import lombok.RequiredArgsConstructor;
import lombok.val;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import ru.itmo.lab.dto.midleware.HotelRoom;
import ru.itmo.lab.dto.responses.HotelResponseDTO;
import ru.itmo.lab.dto.responses.RoomResponseDTO;
import ru.itmo.lab.models.Hotel;
import ru.itmo.lab.models.Room;
import ru.itmo.lab.models.enums.City;
import ru.itmo.lab.repositories.HotelRepository;
import ru.itmo.lab.repositories.RoomRepository;
import ru.itmo.lab.utils.TransactionHelper;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearcherService {
    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;
    private final ModelMapper modelMapper;
    private final TransactionHelper transactionHelper;

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
        var status = transactionHelper.createTransaction("searchHotel");
        List<HotelRoom> hotelRooms = List.of();
        try {
            hotelRooms = hotelRepository.searchRoomsAndHotels(
                    hotelName.orElse(""),
                    city,
                    peopleCount,
                    checkinDate,
                    checkoutDate,
                    minRating.orElse(-1),
                    maxRating.orElse(6),
                    minPrice.orElse(0),
                    maxPrice.orElse(Integer.MAX_VALUE));
            transactionHelper.commit(status);
        } catch (Exception e) {
            transactionHelper.rollback(status);
        }
        return hotelRooms
                .stream()
                .map(HotelRoom::getRoom)
                .map(room -> modelMapper.map(room, RoomResponseDTO.class))
                .collect(Collectors.toList());
    }

    public RoomResponseDTO searchRoom(
            Long roomId
    ) {
        var status = transactionHelper.createTransaction("searchRoom");
        Room hotelRoom = null;
        try {
            hotelRoom = roomRepository.findById(roomId).get();
            transactionHelper.commit(status);
        } catch (Exception e) {
            transactionHelper.rollback(status);
        }
        return modelMapper.map(hotelRoom, RoomResponseDTO.class);
    }
}
