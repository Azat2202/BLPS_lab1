package ru.itmo.booking_service.services;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itmo.booking_service.dto.responses.BookingResponseDTO;
import ru.itmo.booking_service.models.Booking;
import ru.itmo.booking_service.models.Room;
import ru.itmo.booking_service.models.enums.BookingStatus;
import ru.itmo.booking_service.repositories.BookingRepository;
import ru.itmo.lab.kafka.BookingKafkaDTO;

import java.awt.print.Book;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BookingService {
	private final BookingRepository bookingRepository;
	private final ModelMapper modelMapper;
	private final PaymentService paymentService;
	
	@Transactional
	public void createBooking(BookingKafkaDTO bookingKafkaDTO) {
//		System.out.println("Received Message: " + bookingKafkaDTO.toString());
		Booking booking;
		if (!checkRoomBooking(bookingKafkaDTO.getBookingId())) {
			changeStatus(bookingKafkaDTO.getBookingId(), BookingStatus.CANCELED);
		} else {
			booking = changeStatus(bookingKafkaDTO.getBookingId(), BookingStatus.WAITING_PAYMENT);
			paymentService.createPayment(booking);
		}
	}
	
	public boolean checkRoomBooking(Long bookingId) {
		Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new IllegalArgumentException("Booking not found"));
		
		LocalDate startDate = booking.getStartDate();
		LocalDate endDate = booking.getEndDate();
		Room room = modelMapper.map(booking.getRoom(), Room.class);
		
		while (!startDate.isAfter(endDate)) {
			
			List<Booking> bookings = bookingRepository.findAllByRoomAndStartDateLessThanEqualAndEndDateGreaterThanEqual(room, startDate, startDate);
			for (Booking curBooking : bookings) {
				if (curBooking != null && (curBooking.getStatus() == BookingStatus.SUCCESSES || curBooking.getStatus() == BookingStatus.WAITING_PAYMENT) && !Objects.equals(curBooking.getId(), booking.getId())) {
					return false;
				}
			}
			
			startDate = startDate.plusDays(1);
		}
		return true;
	}

	public Booking changeStatus(Long bookingId, BookingStatus status) {
		Booking booking = bookingRepository.findById(bookingId)
				.orElseThrow(() -> new IllegalArgumentException("Booking not found"));
		
		booking.setStatus(status);
		booking = bookingRepository.save(booking);
		return booking;
	}
}