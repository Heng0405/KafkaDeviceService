package com.example.deviceservice.domain.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.data.cassandra.core.mapping.BasicMapId;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.MapId;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

@UserDefinedType(ModelKindRef.TYPE)
public class ModelKindRef extends DomainModel<ModelKindRef> {

    public static final String TYPE = "model_kind_ref";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_KIND = "device_kind";

    @Column(COLUMN_NAME)
    private String name;
    @Column(COLUMN_KIND)
    private String deviceKind;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDeviceKind() {
        return deviceKind;
    }

    public void setDeviceKind(String deviceKind) {
        this.deviceKind = deviceKind;
    }

    @Override
    public MapId getMapId() {
        return BasicMapId.id().with("name", name);
    }

    public static MapId getMapId(String name) {
        return BasicMapId.id().with("name", name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o instanceof ModelKindRef) {
            ModelKindRef that = (ModelKindRef) o;
            return new EqualsBuilder().append(name, that.name)
                                      .append(deviceKind, that.deviceKind)
                                      .isEquals();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(name)
                                    .append(deviceKind)
                                    .toHashCode();
    }

}
