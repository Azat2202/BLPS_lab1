package ru.itmo.lab.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity(name = "application_users")
@Data
@AllArgsConstructor
@Builder(toBuilder = true)
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column
    private String passportNumber;

    @Column
    private String phoneNumber;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Booking> bookings;
}
