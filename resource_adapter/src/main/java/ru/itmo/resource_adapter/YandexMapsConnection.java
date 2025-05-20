package ru.itmo.resource_adapter;

import jakarta.resource.cci.Connection;

public interface YandexMapsConnection extends Connection {
    String generateInvoiceId() throws jakarta.resource.ResourceException;
}
