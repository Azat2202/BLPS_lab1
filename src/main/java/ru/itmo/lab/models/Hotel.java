package ru.itmo.lab.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itmo.lab.models.enums.City;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@Builder(toBuilder = true)
@NoArgsConstructor
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private City city;

    @Column(nullable = false)
    private String address;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "hotel")
    @JsonIgnore
    private List<Room> rooms;

    @Column
    private Integer rating;
}
