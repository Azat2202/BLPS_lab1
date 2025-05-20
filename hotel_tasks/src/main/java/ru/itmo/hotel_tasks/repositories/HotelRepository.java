package ru.itmo.hotel_tasks.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itmo.hotel_tasks.models.Hotel;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {
}