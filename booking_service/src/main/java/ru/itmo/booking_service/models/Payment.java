package ru.itmo.booking_service.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itmo.booking_service.models.enums.PaymentStatus;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@Builder(toBuilder = true)
@NoArgsConstructor
public class Payment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	private User user;
	
	@Column(nullable = false)
	private LocalDate date;
	
	@Column(nullable = false)
	private Integer amount;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private PaymentStatus status;
	
	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	private Booking booking;
}
