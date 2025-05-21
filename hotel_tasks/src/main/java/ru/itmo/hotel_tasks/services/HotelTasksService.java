package ru.itmo.hotel_tasks.services;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.itmo.hotel_tasks.jca.YandexMapsConnection;
import ru.itmo.hotel_tasks.models.Hotel;
import ru.itmo.hotel_tasks.models.Room;
import ru.itmo.hotel_tasks.repositories.HotelRepository;
import ru.itmo.hotel_tasks.repositories.RoomRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HotelTasksService {
    private final YandexMapsConnection yandexMapsConnection;
    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;
    private Logger logger = LoggerFactory.getLogger(HotelTasksService.class);

    @Scheduled(fixedRate = 60000, initialDelay = 0)
    public void generateNewHotels(){
        logger.info("Start generating new hotels");
        List<Hotel> hotels = yandexMapsConnection.getHotels();
        logger.info("Got hotels: ");
        hotels.forEach(hotel -> {
            logger.info(hotel.toString());
            try {
                for (Room room : hotel.getRooms()) {
                    room.setHotel(hotel);
                }
                hotelRepository.save(hotel);
            } catch (DataIntegrityViolationException e) {
                logger.info("Hotel already exists, skipping insert: " + hotel.getName());
            }
        });

    }
}
