package ru.itmo.booking_service.services;

import org.springframework.stereotype.Service;
import ru.itmo.lab.kafka.BookingKafkaDTO;

@Service
public class BookingService {
    public void createBooking(BookingKafkaDTO bookingKafkaDTO){
        System.out.println("Received Message: " + bookingKafkaDTO.toString());
        // todo: тут нужно проверить валидность создания бронирования и добавить запись в таблицу
        //  и создать шедул на 15 минут
    }
}
