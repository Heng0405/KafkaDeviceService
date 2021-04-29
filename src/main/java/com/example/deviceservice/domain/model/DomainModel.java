package com.example.deviceservice.domain.model;

import org.springframework.data.cassandra.core.mapping.MapIdentifiable;

public abstract class DomainModel<DMO> implements MapIdentifiable, Cloneable {

    @SuppressWarnings("unchecked")
    public DMO copy() {
        try {
            return (DMO) this.clone();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
