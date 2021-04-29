package com.example.deviceservice.domain.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.*;

import java.util.Optional;
import java.util.UUID;

@Table(Motherboard.TABLE)
public class Motherboard extends DomainRefModel<Motherboard, MotherboardRef> {

    public static final String TABLE = "motherboard";
    public static final String COLUMN_KUID = "kuid";
    public static final String COLUMN_CREATION_DATE = "creation_date";
    public static final String COLUMN_MODEL_KIND = "model_kind_ref";
    public static final String COLUMN_MODEL_NUMBER = "model_number";
    public static final String COLUMN_SERIAL_NUMBER = "device_serial_number";
    public static final String COLUMN_MAC_VALUE = "mac_address_value";
    public static final String COLUMN_STATUS = "status";

    @PrimaryKeyColumn(name = COLUMN_KUID, type = PrimaryKeyType.PARTITIONED, ordinal = 0)
    private String kuid;
    @Column(COLUMN_CREATION_DATE)
    private UUID creationDate;
    @Column(COLUMN_MODEL_KIND)
    private ModelKindRef modelKindRef;
    @Column(COLUMN_MODEL_NUMBER)
    private String modelNumber;
    @Column(COLUMN_SERIAL_NUMBER)
    private Integer deviceSerialNumber;
    @Column(COLUMN_MAC_VALUE)
    private String macAddressValue;
    @Column(COLUMN_STATUS)
    private String status;

    public String getKuid() {
        return kuid;
    }

    public void setKuid(String kuid) {
        this.kuid = kuid;
    }

    public UUID getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(UUID creationDate) {
        this.creationDate = creationDate;
    }

    public ModelKindRef getModelKindRef() {
        return modelKindRef;
    }

    public void setModelKindRef(ModelKindRef modelKindRef) {
        this.modelKindRef = modelKindRef;
    }

    public String getModelNumber() {
        return modelNumber;
    }

    public void setModelNumber(String modelNumber) {
        this.modelNumber = modelNumber;
    }

    public Integer getDeviceSerialNumber() {
        return deviceSerialNumber;
    }

    public void setDeviceSerialNumber(Integer deviceSerialNumber) {
        this.deviceSerialNumber = deviceSerialNumber;
    }

    public String getMacAddressValue() {
        return macAddressValue;
    }

    public void setMacAddressValue(String macAddressValue) {
        this.macAddressValue = macAddressValue;
    }

    public MotherboardStatus getStatus() {
        return Optional.ofNullable(status).map(MotherboardStatus::valueOf).orElse(MotherboardStatus.CREATED);
    }

    public void setStatus(MotherboardStatus status) {
        this.status = Optional.of(status).map(MotherboardStatus::name).get();
    }

    @Override
    public MotherboardRef toRef() {
        MotherboardRef ref = new MotherboardRef();
        ref.setKuid(this.kuid);
        ref.setModelKindRef(this.modelKindRef.copy());
        ref.setMacAddressValue(this.macAddressValue);
        return ref;
    }

    @Override
    public MapId getMapId() {
        return BasicMapId.id().with("kuid", kuid);
    }

    public static MapId getMapId(String kuid) {
        return BasicMapId.id().with("kuid", kuid);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o instanceof Motherboard) {
            Motherboard that = (Motherboard) o;
            return new EqualsBuilder().append(kuid, that.kuid)
                                      .append(creationDate, that.creationDate)
                                      .append(modelKindRef, that.modelKindRef)
                                      .append(modelNumber, that.modelNumber)
                                      .append(deviceSerialNumber, that.deviceSerialNumber)
                                      .append(macAddressValue, that.macAddressValue)
                                      .append(status, that.status)
                                      .isEquals();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(kuid)
                                    .append(creationDate)
                                    .append(modelKindRef)
                                    .append(modelNumber)
                                    .append(deviceSerialNumber)
                                    .append(macAddressValue)
                                    .append(status)
                                    .toHashCode();
    }

}
