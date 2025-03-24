package ru.itmo.lab.services;

import lombok.RequiredArgsConstructor;
import org.hibernate.NonUniqueResultException;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.itmo.lab.dto.requests.BookingRequestDTO;
import ru.itmo.lab.dto.requests.PaymentRequestDTO;
import ru.itmo.lab.dto.responses.BookingResponseDTO;
import ru.itmo.lab.models.Booking;
import ru.itmo.lab.models.Room;
import ru.itmo.lab.models.User;
import ru.itmo.lab.models.enums.BookingStatus;
import ru.itmo.lab.repositories.BookingRepository;
import ru.itmo.lab.repositories.RoomRepository;
import ru.itmo.lab.repositories.UserRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BookingService {
	private final BookingRepository bookingRepository;
	private final UserRepository userRepository;
	private final RoomRepository roomRepository;
	private final ModelMapper modelMapper;

	public BookingResponseDTO createBooking(BookingRequestDTO bookingRequestDTO) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));

		Room room = roomRepository.findById(bookingRequestDTO.getRoomId())
				.orElseThrow(() -> new IllegalArgumentException("Room not found"));

		Booking booking = Booking.builder()
				.user(user)
				.room(room)
				.startDate(bookingRequestDTO.getStartDate())
				.endDate(bookingRequestDTO.getEndDate())
				.status(BookingStatus.CREATED)
				.build();

		booking = bookingRepository.save(booking);
		return modelMapper.map(booking, BookingResponseDTO.class);
	}

	public boolean checkRoomBooking(BookingResponseDTO bookingResponseDTO) {
		LocalDate startDate = bookingResponseDTO.getStartDate();
		LocalDate endDate = bookingResponseDTO.getEndDate();

		Room room = modelMapper.map(bookingResponseDTO.getRoom(), Room.class);

		while (!startDate.isAfter(endDate)) {

			List<Booking> bookings = bookingRepository.findAllByRoomAndStartDateLessThanEqualAndEndDateGreaterThanEqual(room, startDate, startDate);
			for (Booking booking: bookings) {
				if (booking != null && booking.getStatus() == BookingStatus.SUCCESSES && !Objects.equals(booking.getId(), bookingResponseDTO.getId())) {
					return false;
				}
			}

			startDate = startDate.plusDays(1);
		}
		return true;
	}

	public BookingResponseDTO doBookingSuccess(PaymentRequestDTO paymentRequestDTO) {
		Booking booking = bookingRepository.findById(paymentRequestDTO.getBookingId())
				.orElseThrow(() -> new IllegalArgumentException("Booking not found"));

		booking.setStatus(BookingStatus.SUCCESSES);
		booking = bookingRepository.save(booking);

		return modelMapper.map(booking, BookingResponseDTO.class);
	}
}
