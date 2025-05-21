package ru.itmo.hotel_tasks.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itmo.hotel_tasks.models.Hotel;
import ru.itmo.hotel_tasks.models.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
}