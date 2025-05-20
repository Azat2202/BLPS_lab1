package ru.itmo.resource_adapter;

import jakarta.resource.Referenceable;
import java.io.Serializable;

public interface YandexMapsConnectionFactory extends Serializable, Referenceable {
    YandexMapsConnection getConnection() throws jakarta.resource.ResourceException;
}
