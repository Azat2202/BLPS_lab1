package ru.itmo.lab.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;
import ru.itmo.lab.dto.requests.PaymentRequestDTO;
import ru.itmo.lab.models.Payment;
import ru.itmo.lab.models.enums.PaymentStatus;
import ru.itmo.lab.repositories.PaymentRepository;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Service
public class Scheduler {
	private final PaymentRepository paymentRepository;
	private final Map<Long, ScheduledFuture<?>> tasks = new ConcurrentHashMap<>();
	private final ThreadPoolTaskScheduler threadPoolTaskScheduler;
	
	@Autowired
	public Scheduler(PaymentRepository paymentRepository) {
		this.paymentRepository = paymentRepository;
		this.threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
		this.threadPoolTaskScheduler.setPoolSize(5);
		this.threadPoolTaskScheduler.setThreadNamePrefix("PaymentScheduler-");
		this.threadPoolTaskScheduler.initialize();
	}
	
	public void schedulePaymentExpiration(Payment payment) {
		System.out.println("Planning expiration of payment with ID " + payment.getId());
		
		ScheduledFuture<?> scheduledTask = threadPoolTaskScheduler.schedule(
				() -> {
					synchronized (this) {
						if (!tasks.containsKey(payment.getId())) {
							return;
						}
						try {
							payment.setStatus(PaymentStatus.EXPIRED);
							paymentRepository.save(payment);
							System.out.println("Payment " + payment.getId() + " expired");
						} catch (Exception e) {
							System.out.println("Error with planning expired: " + e.getMessage());
						} finally {
							tasks.remove(payment.getId());
						}
					}
				},
				triggerContext -> {
					long delay = TimeUnit.SECONDS.toMillis(900);
					return new Date(System.currentTimeMillis() + delay).toInstant();
				}
		);
		
		tasks.put(payment.getId(), scheduledTask);
	}

	
	public void cancelPaymentExpiration(PaymentRequestDTO paymentRequestDTO) {
		Long paymentId = paymentRequestDTO.getId();
		
		ScheduledFuture<?> scheduledTask = tasks.get(paymentId);
		if (scheduledTask != null && !scheduledTask.isDone()) {
			scheduledTask.cancel(true);
			tasks.remove(paymentId);
			System.out.println("Payment expiration canceled");
			Payment payment = paymentRepository.findById(paymentId)
					.orElseThrow(() -> new IllegalArgumentException("Payment not found"));
			payment.setStatus(PaymentStatus.PAID);
			paymentRepository.save(payment);
		}
	}
}
