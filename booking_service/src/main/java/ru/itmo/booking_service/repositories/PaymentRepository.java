package ru.itmo.booking_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itmo.booking_service.models.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

}
