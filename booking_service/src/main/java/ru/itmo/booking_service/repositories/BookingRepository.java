package ru.itmo.booking_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.itmo.booking_service.models.Booking;
import ru.itmo.booking_service.models.Room;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
	
	List<Booking> findAllByRoomAndStartDateLessThanEqualAndEndDateGreaterThanEqual(Room room, LocalDate startDate, LocalDate endDate);
	@Query("SELECT b FROM Booking b " +
			"JOIN b.room r " +
			"JOIN r.hotel h " +
			"WHERE h.name ILIKE %:hotelName% " +
			"AND (:checkinDate < b.startDate AND :checkoutDate > b.endDate) " +
			"ORDER BY b.startDate")
	List<Booking> findBookingsByHotelAndDates(
			@Param("hotelName") String hotelName,
			@Param("checkinDate") LocalDate checkinDate,
			@Param("checkoutDate") LocalDate checkoutDate
	);
}
