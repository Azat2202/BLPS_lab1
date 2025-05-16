package ru.itmo.lab.services;

import com.atomikos.icatch.TransactionService;
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
import ru.itmo.lab.utils.TransactionHelper;

@Service
@RequiredArgsConstructor
public class HotelService {
	private final BookingRepository bookingRepository;
	private final UserRepository userRepository;
	private final HotelRepository hotelRepository;
	private final ModelMapper modelMapper;
	private final TransactionHelper transactionHelper;

	public HotelResponseDTO create(HotelRequestDTO hotelRequestDTO) {
		var status = transactionHelper.createTransaction("createHotel");

        try {
            Hotel hotel = new Hotel();
            hotel.setName(hotelRequestDTO.getName());
            hotel.setAddress(hotelRequestDTO.getAddress());
            hotel.setRating(hotelRequestDTO.getRating());
            hotel.setCity(hotelRequestDTO.getCity());
            hotel.setDescription(hotelRequestDTO.getDescription());

            hotel = hotelRepository.save(hotel);
			transactionHelper.commit(status);
            return modelMapper.map(hotel, HotelResponseDTO.class);
        } catch (Exception e) {
            transactionHelper.rollback(status);
			return null;
        }
    }
	
	public HotelResponseDTO delete(Long hotelId) {
		var status = transactionHelper.createTransaction("deleteHotel");
        try {
            Hotel hotel = hotelRepository.findById(hotelId)
                    .orElseThrow(() -> new IllegalArgumentException("Hotel not found"));
            hotelRepository.delete(hotel);
			transactionHelper.commit(status);
            return modelMapper.map(hotel, HotelResponseDTO.class);
        } catch (IllegalArgumentException e) {
            transactionHelper.rollback(status);
			return null;
        }
    }
}
