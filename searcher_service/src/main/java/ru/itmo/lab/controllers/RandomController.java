package ru.itmo.lab.controllers;

import com.atomikos.datasource.ResourceException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itmo.resource_adapter.YandexMapsConnection;
import ru.itmo.resource_adapter.impl.YandexMapsConnectionImpl;

@RestController
@RequestMapping("/api/random")
@RequiredArgsConstructor
@Tag(name = "RandomController", description = "Контроллер для случайных чисел")
public class RandomController {

    @GetMapping("/random")
    @Operation(summary = "Случайное число", description = "Создаёт случайное число")
    public ResponseEntity<?> getRandomInvoiceId() {
        YandexMapsConnection connection = null;
        try {
            connection = getConnectionDirectly();
            String invoiceId = connection.generateInvoiceId();

            return ResponseEntity.ok("{\"invoiceId\": \"" + invoiceId + "\"}");

        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("{\"error\": \"ResourceException during direct connection usage: " + e.getMessage() + "\"}");
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (ResourceException ignored) {
                }
            }
        }
    }

    private YandexMapsConnection getConnectionDirectly() throws ResourceException {
        return new YandexMapsConnectionImpl(null);
    }
}
