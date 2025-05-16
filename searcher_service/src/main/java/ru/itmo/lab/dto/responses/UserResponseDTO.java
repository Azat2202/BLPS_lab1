package ru.itmo.lab.dto.responses;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itmo.lab.models.Booking;
import ru.itmo.lab.models.enums.Privilege;
import ru.itmo.lab.models.enums.Role;

import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {
    private Long id;

    private String username;
}
