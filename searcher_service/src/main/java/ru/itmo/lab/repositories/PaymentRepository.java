package ru.itmo.lab.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itmo.lab.models.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

}
