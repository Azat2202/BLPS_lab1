package ru.itmo.lab.services;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.itmo.lab.dto.requests.BookingRequestDTO;
import ru.itmo.lab.dto.requests.PaymentRequestDTO;
import ru.itmo.lab.dto.requests.RoomRequestDTO;
import ru.itmo.lab.dto.responses.BookingResponseDTO;
import ru.itmo.lab.dto.responses.RoomResponseDTO;
import ru.itmo.lab.models.Booking;
import ru.itmo.lab.models.Hotel;
import ru.itmo.lab.models.Room;
import ru.itmo.lab.models.User;
import ru.itmo.lab.models.enums.BookingStatus;
import ru.itmo.lab.repositories.BookingRepository;
import ru.itmo.lab.repositories.HotelRepository;
import ru.itmo.lab.repositories.RoomRepository;
import ru.itmo.lab.repositories.UserRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class RoomService {
	private final BookingRepository bookingRepository;
	private final UserRepository userRepository;
	private final RoomRepository roomRepository;
	private final HotelRepository hotelRepository;
	private final ModelMapper modelMapper;

	public RoomResponseDTO create(RoomRequestDTO roomRequestDTO) {
		Hotel hotel = hotelRepository.findById(roomRequestDTO.getHotelId())
				.orElseThrow(() -> new IllegalArgumentException("Hotel not found"));
				
		Room room = new Room();
		room.setName(roomRequestDTO.getName());
		room.setCapacity(roomRequestDTO.getCapacity());
		room.setPrice(roomRequestDTO.getPrice());
		room.setHotel(hotel);

		room = roomRepository.save(room);
		return modelMapper.map(room, RoomResponseDTO.class);
	}
	
	public RoomResponseDTO delete(Long roomId) {
		Room room = roomRepository.findById(roomId)
						.orElseThrow(() -> new IllegalArgumentException("Room not found"));
		roomRepository.delete(room);
		return modelMapper.map(room, RoomResponseDTO.class);
	}
}
