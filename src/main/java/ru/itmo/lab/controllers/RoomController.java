package ru.itmo.lab.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itmo.lab.dto.requests.RoomRequestDTO;
import ru.itmo.lab.dto.responses.RoomResponseDTO;
import ru.itmo.lab.repositories.RoomRepository;
import ru.itmo.lab.services.RoomService;

@RestController
@RequestMapping("/api/room")
@RequiredArgsConstructor
@Tag(name = "RoomController", description = "Контроллер для получения информации о комнатах")
public class RoomController {
    private final RoomService roomService;

    @PreAuthorize("hasAuthority('CREATE_ROOM')")
    @GetMapping("/create")
    @Operation(summary = "Создать новую комнату в отеле", description = "Создает новую комнату в отеле. Доступно только владельцам отелей")
    public ResponseEntity<?> create(@Valid RoomRequestDTO room) {
        try {
            RoomResponseDTO roomResponseDTO = roomService.create(room);
            return ResponseEntity.ok(roomResponseDTO);
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }
    
    @PreAuthorize("hasAuthority('DELETE_ROOM')")
    @DeleteMapping("/delete")
    @Operation(summary = "Удалить комнату в отеле", description = "Удалить комнату в отеле. Доступно только владельцам отелей")
    public ResponseEntity<?> delete(@Valid Long roomId) {
        try {
            RoomResponseDTO roomResponseDTO = roomService.delete(roomId);
            return ResponseEntity.ok(roomResponseDTO);
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }
}
