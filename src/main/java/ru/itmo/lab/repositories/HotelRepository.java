package ru.itmo.lab.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itmo.lab.models.Hotel;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {

}
