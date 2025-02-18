package ru.itmo.lab.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itmo.lab.models.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

}
