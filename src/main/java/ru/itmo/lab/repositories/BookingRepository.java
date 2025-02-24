package ru.itmo.lab.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itmo.lab.models.Booking;
import ru.itmo.lab.models.Room;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
	
	List<Booking> findAllByRoomAndStartDateLessThanEqualAndEndDateGreaterThanEqual(Room room, LocalDate startDate, LocalDate endDate);

}
