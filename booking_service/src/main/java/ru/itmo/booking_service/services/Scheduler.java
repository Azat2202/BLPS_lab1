package ru.itmo.booking_service.services;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;
import ru.itmo.booking_service.dto.requests.PaymentRequestDTO;
import ru.itmo.booking_service.models.Payment;
import ru.itmo.booking_service.models.enums.PaymentStatus;
import ru.itmo.booking_service.repositories.PaymentRepository;
import ru.itmo.booking_service.utils.TransactionHelper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class Scheduler {
	private final PaymentRepository paymentRepository;
	
	@Scheduled(fixedDelay = 60000)
	public void deleteExpiredPayments() {
		List<Payment> unpaidPayments = paymentRepository
				.findByStatusAndDateBefore(
					PaymentStatus.CREATED,
						LocalDateTime.now().minusMinutes(15)
				);
		
		for (Payment payment : unpaidPayments) {
			payment.setStatus(PaymentStatus.EXPIRED);
			paymentRepository.save(payment);
		}
	}
}
