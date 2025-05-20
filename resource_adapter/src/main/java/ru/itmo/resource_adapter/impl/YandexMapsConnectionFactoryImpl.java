package ru.itmo.resource_adapter.impl;

import jakarta.resource.ResourceException;
import jakarta.resource.cci.ConnectionSpec;
import jakarta.resource.cci.RecordFactory;
import jakarta.resource.cci.ResourceAdapterMetaData;
import jakarta.resource.spi.ConnectionManager;
import jakarta.resource.cci.Connection;
import jakarta.resource.spi.ManagedConnectionFactory;
import ru.itmo.resource_adapter.YandexMapsConnection;
import ru.itmo.resource_adapter.YandexMapsConnectionFactory;

import javax.naming.NamingException;
import javax.naming.Reference;
import java.util.Objects;

public class YandexMapsConnectionFactoryImpl implements YandexMapsConnectionFactory {

    private final ManagedConnectionFactory mcf;
    private final ConnectionManager cm;
    private Reference reference;

    public YandexMapsConnectionFactoryImpl(ManagedConnectionFactory mcf, ConnectionManager cm) {
        this.mcf = mcf;
        this.cm = cm;
    }

    @Override
    public YandexMapsConnection getConnection() throws ResourceException {
        if (cm == null) {
            throw new ResourceException("ConnectionManager is not available. Cannot provide a managed connection.");
        }
        return (YandexMapsConnection) cm.allocateConnection(mcf, null);
    }

    @Override
    public Reference getReference() throws NamingException {
        return reference;
    }

    @Override
    public void setReference(Reference reference) {
        this.reference = reference;
    }

    public ResourceAdapterMetaData getMetaData() throws ResourceException {
        throw new ResourceException("getMetaData not supported");
    }

    public RecordFactory getRecordFactory() throws ResourceException {
        throw new ResourceException("getRecordFactory not supported");
    }

    public Connection getConnection(ConnectionSpec properties) throws ResourceException {
        throw new ResourceException("getConnection with ConnectionSpec not supported");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        YandexMapsConnectionFactoryImpl that = (YandexMapsConnectionFactoryImpl) o;
        return Objects.equals(mcf, that.mcf) && Objects.equals(cm, that.cm) && Objects.equals(reference, that.reference);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mcf, cm, reference);
    }
}