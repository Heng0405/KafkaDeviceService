package com.example.deviceservice.domain.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.data.cassandra.core.mapping.BasicMapId;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.MapId;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

@UserDefinedType(FunctionalityRef.TYPE)
public class FunctionalityRef extends DomainModel<FunctionalityRef> {

    public static final String TYPE = "functionality_ref";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_KIND = "kind";

    @Column(COLUMN_NAME)
    private String name;
    @Column(COLUMN_KIND)
    private String kind;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

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
        } else if (o instanceof FunctionalityRef) {
            FunctionalityRef that = (FunctionalityRef) o;
            return new EqualsBuilder().append(name, that.name)
                                      .append(kind, that.kind)
                                      .isEquals();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(name)
                                    .append(kind)
                                    .toHashCode();
    }

}
