package ru.itmo.lab.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itmo.lab.models.enums.Privilege;
import ru.itmo.lab.models.enums.Role;

import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@Builder(toBuilder = true)
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(name = "passport_number")
    private String passportNumber;

    @Column(name = "phone_number")
    private String phoneNumber;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Booking> bookings;
    
    @Enumerated(EnumType.STRING)
    private Role role;
    
    public Set<Privilege> getPrivileges() {
        return role.getPrivileges();
    }
}
