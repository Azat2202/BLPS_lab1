package ru.itmo.lab.services;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.itmo.lab.dto.requests.HotelRequestDTO;
import ru.itmo.lab.dto.responses.HotelResponseDTO;
import ru.itmo.lab.models.Hotel;
import ru.itmo.lab.models.Hotel;
import ru.itmo.lab.repositories.BookingRepository;
import ru.itmo.lab.repositories.HotelRepository;
import ru.itmo.lab.repositories.HotelRepository;
import ru.itmo.lab.repositories.UserRepository;

@Service
@RequiredArgsConstructor
public class HotelService {
	private final BookingRepository bookingRepository;
	private final UserRepository userRepository;
	private final HotelRepository hotelRepository;
	private final ModelMapper modelMapper;

	public HotelResponseDTO create(HotelRequestDTO hotelRequestDTO) {

		Hotel hotel = new Hotel();
		hotel.setName(hotelRequestDTO.getName());
		hotel.setAddress(hotelRequestDTO.getAddress());
		hotel.setRating(hotelRequestDTO.getRating());
		hotel.setCity(hotelRequestDTO.getCity());
		hotel.setDescription(hotelRequestDTO.getDescription());

		hotel = hotelRepository.save(hotel);
		return modelMapper.map(hotel, HotelResponseDTO.class);
	}
	
	public HotelResponseDTO delete(Long hotelId) {
		Hotel hotel = hotelRepository.findById(hotelId)
						.orElseThrow(() -> new IllegalArgumentException("Hotel not found"));
		hotelRepository.delete(hotel);
		return modelMapper.map(hotel, HotelResponseDTO.class);
	}
}
