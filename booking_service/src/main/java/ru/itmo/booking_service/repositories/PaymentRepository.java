package ru.itmo.booking_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itmo.booking_service.models.Payment;
import ru.itmo.booking_service.models.enums.PaymentStatus;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
	
	List<Payment> findByStatusAndDateBefore(PaymentStatus paymentStatus, LocalDateTime localDateTime);
}
