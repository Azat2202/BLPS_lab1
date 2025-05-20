package ru.itmo.lab.services;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.itmo.lab.controllers.BookingController;
import ru.itmo.lab.dto.requests.BookingRequestDTO;
import ru.itmo.lab.dto.responses.BookingResponseDTO;
import ru.itmo.lab.kafka.BookingKafkaDTO;
import ru.itmo.lab.models.Booking;
import ru.itmo.lab.models.Room;
import ru.itmo.lab.models.User;
import ru.itmo.lab.models.enums.BookingStatus;
import ru.itmo.lab.repositories.BookingRepository;
import ru.itmo.lab.repositories.RoomRepository;
import ru.itmo.lab.repositories.UserRepository;
import ru.itmo.lab.utils.TransactionHelper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final KafkaTemplate<Long, BookingKafkaDTO> bookingProducer;
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final TransactionHelper transactionHelper;
    private final ModelMapper modelMapper;
    
    @Value("${spring.kafka.producer.topic-name}")
    private String topicName;

    public Long createBooking(BookingRequestDTO bookingRequestDTO) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));

        Room room = roomRepository.findById(bookingRequestDTO.getRoomId())
                .orElseThrow(() -> new IllegalArgumentException("Room not found"));
        
        Booking booking = new Booking();
        
        booking.setUser(user);
        booking.setRoom(room);
        booking.setStartDate(bookingRequestDTO.getStartDate());
        booking.setEndDate(bookingRequestDTO.getEndDate());
        booking.setStatus(BookingStatus.CREATED);

        booking = bookingRepository.save(booking);
        
        BookingKafkaDTO bookingKafkaDTO = new BookingKafkaDTO(booking.getId(), user.getId());
        bookingProducer.send(topicName, bookingKafkaDTO);
        return booking.getId();
    }
    
    public BookingStatus checkBookingStatus(@Valid Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new IllegalArgumentException("Booking not found"));
        
        return booking.getStatus();
    }
    
    public List<BookingResponseDTO> find(String hotelName, LocalDate checkinDate, LocalDate checkoutDate) {
		var status = transactionHelper.createTransaction("find");

        try {
            List<Booking> bookings = bookingRepository.findBookingsByHotelAndDates(hotelName, checkinDate, checkoutDate);
            List<BookingResponseDTO> answer = new ArrayList<>();
            for (Booking booking : bookings) {
                answer.add(modelMapper.map(booking, BookingResponseDTO.class));
            }
			transactionHelper.commit(status);
            return answer;
        } catch (Exception e) {
            transactionHelper.rollback(status);
			return null;
        }
    }
}
