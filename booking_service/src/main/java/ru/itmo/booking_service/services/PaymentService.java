package ru.itmo.booking_service.services;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.itmo.booking_service.dto.responses.BookingResponseDTO;
import ru.itmo.booking_service.dto.responses.PaymentResponseDTO;
import ru.itmo.booking_service.models.Booking;
import ru.itmo.booking_service.models.Payment;
import ru.itmo.booking_service.models.enums.PaymentStatus;
import ru.itmo.booking_service.repositories.PaymentRepository;
import ru.itmo.booking_service.utils.TransactionHelper;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PaymentService {
	private final PaymentRepository paymentRepository;
	private final ModelMapper modelMapper;
	private final TransactionHelper transactionHelper;
	
	public void createPayment(Booking booking) {
//		var status = transactionHelper.createTransaction("createPayment");
//
//        try {
        Payment createdPayment = Payment.builder()
		        .user(booking.getUser())
                .amount(booking.getRoom().getPrice())
                .status(PaymentStatus.CREATED)
                .date(LocalDateTime.now())
                .booking(booking)
                .build();

        paymentRepository.save(createdPayment);
//		transactionHelper.commit(status);
//        } catch (Exception e) {
//			transactionHelper.rollback(status);
//            return null;
//        }
    }
}
