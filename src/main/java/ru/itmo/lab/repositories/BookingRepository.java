package ru.itmo.lab.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itmo.lab.models.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

}
