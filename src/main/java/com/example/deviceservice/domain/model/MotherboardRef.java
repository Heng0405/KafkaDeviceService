package com.example.deviceservice.domain.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.data.cassandra.core.mapping.BasicMapId;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.MapId;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

@UserDefinedType(MotherboardRef.TYPE)
public class MotherboardRef extends DomainModel<MotherboardRef> {

    public static final String TYPE = "motherboard_ref";
    public static final String COLUMN_KUID = "kuid";
    public static final String COLUMN_MAC_VALUE = "mac_address_value";
    public static final String COLUMN_MODEL_KIND = "model_kind_ref";

    @Column(COLUMN_KUID)
    private String kuid;
    @Column(COLUMN_MAC_VALUE)
    private String macAddressValue;
    @Column(COLUMN_MODEL_KIND)
    private ModelKindRef modelKindRef;

    public static MapId getMapId(String kuid) {
        return BasicMapId.id().with("kuid", kuid);
    }

    public String getKuid() {
        return kuid;
    }

    public void setKuid(String kuid) {
        this.kuid = kuid;
    }

    public String getMacAddressValue() {
        return macAddressValue;
    }

    public void setMacAddressValue(String macAddressValue) {
        this.macAddressValue = macAddressValue;
    }

    public ModelKindRef getModelKindRef() {
        return modelKindRef;
    }

    public void setModelKindRef(ModelKindRef modelKindRef) {
        this.modelKindRef = modelKindRef;
    }

    @Override
    public MapId getMapId() {
        return BasicMapId.id().with("kuid", kuid);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o instanceof MotherboardRef) {
            MotherboardRef that = (MotherboardRef) o;
            return new EqualsBuilder().append(kuid, that.kuid)
                                      .append(macAddressValue, that.macAddressValue)
                                      .append(modelKindRef, that.modelKindRef)
                                      .isEquals();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(kuid)
                                    .append(macAddressValue)
                                    .append(modelKindRef)
                                    .toHashCode();
    }

}
