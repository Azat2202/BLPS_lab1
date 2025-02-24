package ru.itmo.lab.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.itmo.lab.dto.midleware.HotelRoom;
import ru.itmo.lab.models.Hotel;
import ru.itmo.lab.models.enums.City;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {
    @Query("SELECT new ru.itmo.lab.dto.midleware.HotelRoom(h, r) FROM Hotel h " +
            "JOIN h.rooms r " +
            "LEFT JOIN Feedback f ON f.hotel = h " +
            "LEFT JOIN Booking b ON b.room = r " +
            "AND (:checkinDate <= b.date AND :checkoutDate >= b.date) " +
            "WHERE (h.name ilike ('%' || :hotelName || '%')) " +
            "AND (h.city = :city) " +
            "AND (r.price >= :minPrice) " +
            "AND (r.price <= :maxPrice) " +
            "AND r.capacity >= :peopleCount " +
            "AND b.id IS NULL " +
            "GROUP BY h.id, r.id " +
            "HAVING (:minRating IS NULL OR COALESCE(AVG(f.rating), 0) >= :minRating) " +
            "AND (:maxRating IS NULL OR COALESCE(AVG(f.rating), 0) <= :maxRating)")
    List<HotelRoom> searchRoomsAndHotels(
            @Param("hotelName") String hotelName,
            @Param("city") City city,
            @Param("peopleCount") Integer peopleCount,
            @Param("checkinDate") LocalDate checkinDate,
            @Param("checkoutDate") LocalDate checkoutDate,
            @Param("minRating") Integer minRating,
            @Param("maxRating") Integer maxRating,
            @Param("minPrice") Integer minPrice,
            @Param("maxPrice") Integer maxPrice
    );

}
