package ru.itmo.lab.services;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.itmo.lab.dto.requests.BookingRequestDTO;
import ru.itmo.lab.dto.responses.BookingResponseDTO;
import ru.itmo.lab.models.Booking;
import ru.itmo.lab.models.Room;
import ru.itmo.lab.models.User;
import ru.itmo.lab.models.enums.BookingStatus;
import ru.itmo.lab.repositories.BookingRepository;
import ru.itmo.lab.repositories.RoomRepository;
import ru.itmo.lab.repositories.UserRepository;

@Service
@RequiredArgsConstructor
public class BookingService {
	private final BookingRepository bookingRepository;
	private final UserRepository userRepository;
	private final RoomRepository roomRepository;
	private final ModelMapper modelMapper;
	
	public BookingResponseDTO createBooking(BookingRequestDTO bookingRequestDTO) {
		String username = "";
		User user = userRepository.findByUsername(username);
		
		Room room = roomRepository.findById(bookingRequestDTO.getRoomId())
				.orElseThrow(() -> new IllegalArgumentException("Room not found"));
		
		Booking booking = Booking.builder()
				.user(user)
				.room(room)
				.date(bookingRequestDTO.getDate())
				.status(BookingStatus.CREATED)
				.build();
		
		booking = bookingRepository.save(booking);
		return modelMapper.map(booking, BookingResponseDTO.class);
	}
}
