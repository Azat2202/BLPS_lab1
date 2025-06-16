package ru.itmo.hotel_tasks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
public class HotelTasksApplication {
    public static void main(String[] args) {
        SpringApplication.run(HotelTasksApplication.class, args);
    }
}
