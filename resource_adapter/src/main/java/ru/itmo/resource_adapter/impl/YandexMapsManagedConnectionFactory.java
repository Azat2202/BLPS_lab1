package ru.itmo.resource_adapter.impl;


import jakarta.resource.ResourceException;
import jakarta.resource.spi.*;


import javax.security.auth.Subject;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

public class YandexMapsManagedConnectionFactory implements ManagedConnectionFactory, ResourceAdapterAssociation, Serializable {

    private static final long serialVersionUID = 1L;
    private ResourceAdapter resourceAdapter;
    private PrintWriter logWriter;

    public YandexMapsManagedConnectionFactory() {
    }

    public YandexMapsManagedConnectionFactory(ResourceAdapter resourceAdapter) {
        this.resourceAdapter = resourceAdapter;
    }

    @Override
    public Object createConnectionFactory(ConnectionManager cxManager) throws ResourceException {
        return new YandexMapsConnectionFactoryImpl(this, cxManager);
    }

    @Override
    public Object createConnectionFactory() throws ResourceException {
        return new YandexMapsConnectionFactoryImpl(this, null); // Или бросить исключение, если не поддерживается
    }

    @Override
    public ManagedConnection createManagedConnection(Subject subject, ConnectionRequestInfo cxRequestInfo) throws ResourceException {
        return new YandexMapsManagedConnection(this);
    }

    @Override
    public ManagedConnection matchManagedConnections(Set connectionSet, Subject subject, ConnectionRequestInfo cxRequestInfo) throws ResourceException {
        for (Object obj : connectionSet) {
            if (obj instanceof YandexMapsManagedConnection) {
                return (YandexMapsManagedConnection) obj;
            }
        }
        return null;
    }

    @Override
    public void setLogWriter(PrintWriter out) {
        this.logWriter = out;
    }

    @Override
    public PrintWriter getLogWriter() {
        return logWriter;
    }

    @Override
    public ResourceAdapter getResourceAdapter() {
        return resourceAdapter;
    }

    @Override
    public void setResourceAdapter(ResourceAdapter ra) throws ResourceException {
        if (!(ra instanceof YandexMapsResourceAdapter)) {
            throw new ResourceException("ResourceAdapter must be of type AviasalesResourceAdapter");
        }
        this.resourceAdapter = ra;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        YandexMapsManagedConnectionFactory that = (YandexMapsManagedConnectionFactory) o;
        return Objects.equals(resourceAdapter, that.resourceAdapter) && Objects.equals(logWriter, that.logWriter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(resourceAdapter, logWriter);
    }
}
