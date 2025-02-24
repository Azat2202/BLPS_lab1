package ru.itmo.lab.services;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.itmo.lab.dto.requests.BookingRequestDTO;
import ru.itmo.lab.dto.responses.BookingResponseDTO;
import ru.itmo.lab.dto.responses.PaymentResponseDTO;
import ru.itmo.lab.models.Booking;
import ru.itmo.lab.models.Payment;
import ru.itmo.lab.models.Room;
import ru.itmo.lab.models.User;
import ru.itmo.lab.models.enums.BookingStatus;
import ru.itmo.lab.models.enums.PaymentStatus;
import ru.itmo.lab.repositories.BookingRepository;
import ru.itmo.lab.repositories.PaymentRepository;
import ru.itmo.lab.repositories.RoomRepository;
import ru.itmo.lab.repositories.UserRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PaymentService {
	private final PaymentRepository paymentRepository;
	private final ModelMapper modelMapper;
	
	public PaymentResponseDTO createPayment(BookingResponseDTO createdBooking) {
		Payment createdPayment = Payment.builder()
				.user(createdBooking.getUser())
				.amount(createdBooking.getRoom().getPrice())
				.status(PaymentStatus.CREATED)
				.date(LocalDate.now())
				.build();
		
		createdPayment = paymentRepository.save(createdPayment);
		return modelMapper.map(createdPayment, PaymentResponseDTO.class);
	}
}
