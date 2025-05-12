package ru.itmo.lab.services;

import com.atomikos.icatch.TransactionService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;
import ru.itmo.lab.dto.requests.PaymentRequestDTO;
import ru.itmo.lab.models.Payment;
import ru.itmo.lab.models.enums.PaymentStatus;
import ru.itmo.lab.repositories.PaymentRepository;
import ru.itmo.lab.utils.TransactionHelper;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class Scheduler {
	private final PaymentRepository paymentRepository;
	private Map<Long, ScheduledFuture<?>> tasks = new ConcurrentHashMap<>();
	private ThreadPoolTaskScheduler threadPoolTaskScheduler;
	private final  TransactionHelper transactionHelper;
	
	@PostConstruct
	public void init() {
		this.threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
		this.threadPoolTaskScheduler.setPoolSize(5);
		this.threadPoolTaskScheduler.setThreadNamePrefix("PaymentScheduler-");
		this.threadPoolTaskScheduler.initialize();
	}
	
	public void schedulePaymentExpiration(Payment payment) {
		System.out.println("Planning expiration of payment with ID " + payment.getId());
		ScheduledFuture<?> scheduledTask = threadPoolTaskScheduler.schedule(
				() -> {
					var status = transactionHelper.createTransaction("schedulePaymentExpiration");
					try {
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
						transactionHelper.commit(status);
                    } catch (Exception e) {
						transactionHelper.rollback(status);
						System.out.println("Error with planning expiration: " + e.getMessage());
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
