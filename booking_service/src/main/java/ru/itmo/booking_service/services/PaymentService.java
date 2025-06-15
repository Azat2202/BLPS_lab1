package ru.itmo.booking_service.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itmo.booking_service.dto.responses.BookingResponseDTO;
import ru.itmo.booking_service.dto.responses.PaymentResponseDTO;
import ru.itmo.booking_service.models.Booking;
import ru.itmo.booking_service.models.Payment;
import ru.itmo.booking_service.models.enums.PaymentStatus;
import ru.itmo.booking_service.repositories.BookingRepository;
import ru.itmo.booking_service.repositories.PaymentRepository;
import ru.itmo.booking_service.utils.TransactionHelper;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final ModelMapper modelMapper;
    private final TransactionHelper transactionHelper;
    private final BookingRepository bookingRepository;

    public void createPayment(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new EntityNotFoundException("Booking not found"));

        Payment createdPayment = Payment.builder()
                .user(booking.getUser())
                .amount(booking.getRoom().getPrice())
                .status(PaymentStatus.CREATED)
                .date(LocalDateTime.now())
                .booking(booking)
                .build();

        paymentRepository.save(createdPayment);
    }
}
