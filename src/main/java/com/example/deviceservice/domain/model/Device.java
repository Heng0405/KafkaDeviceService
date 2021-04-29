package com.example.deviceservice.domain.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.*;

import java.time.Instant;
import java.util.Optional;
import java.util.Set;

@Table(Device.TABLE)
public class Device extends DomainModel<Device> {

    public static final String TABLE = "device";
    public static final String COLUMN_MODEL_NUMBER = "model_number";
    public static final String COLUMN_SERIAL_NUMBER = "serial_number";
    public static final String COLUMN_CREATION_DATE = "creation_date";
    public static final String COLUMN_UPDATE_DATE = "update_date";
    public static final String COLUMN_LAST_CONNECTION = "last_connection_date";
    public static final String COLUMN_STATUS = "status";
    public static final String COLUMN_MOTHERBOARD = "motherboard_ref";
    public static final String COLUMN_BRAND = "brand";
    public static final String COLUMN_FUNCTIONALITIES = "functionality_refs";
    public static final String COLUMN_CONTRACT = "contract_ref";


    @PrimaryKeyColumn(name = COLUMN_MODEL_NUMBER, type = PrimaryKeyType.PARTITIONED, ordinal = 0)
    private String modelNumber;
    @PrimaryKeyColumn(name = COLUMN_SERIAL_NUMBER, type = PrimaryKeyType.PARTITIONED, ordinal = 1)
    private int serialNumber;
    @Column(COLUMN_CREATION_DATE)
    private Instant creationDate;
    @Column(COLUMN_UPDATE_DATE)
    private Instant updateDate;
    @Column(COLUMN_LAST_CONNECTION)
    private Instant lastConnectionDate;
    @Column(COLUMN_STATUS)
    private String status;
    @Column(COLUMN_MOTHERBOARD)
    private MotherboardRef motherboardRef;
    @Column(COLUMN_BRAND)
    private String brand;
    @Column(COLUMN_FUNCTIONALITIES)
    private Set<FunctionalityRef> functionalityRefs;
    @Column(COLUMN_CONTRACT)
    private ContractRef contractRef;

    public String getModelNumber() {
        return modelNumber;
    }

    public void setModelNumber(String modelNumber) {
        this.modelNumber = modelNumber;
    }

    public int getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(int serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Instant getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }

    public Instant getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Instant updateDate) {
        this.updateDate = updateDate;
    }

    public Instant getLastConnectionDate() {
        return lastConnectionDate;
    }

    public void setLastConnectionDate(Instant lastConnectionDate) {
        this.lastConnectionDate = lastConnectionDate;
    }

    public DeviceStatus getStatus() {
        return Optional.ofNullable(status).map(DeviceStatus::valueOf).orElse(DeviceStatus.CREATED);
    }

    public void setStatus(DeviceStatus status) {
        this.status = Optional.of(status).map(DeviceStatus::name).get();
    }

    public MotherboardRef getMotherboardRef() {
        return motherboardRef;
    }

    public void setMotherboardRef(MotherboardRef motherboardRef) {
        this.motherboardRef = motherboardRef;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Set<FunctionalityRef> getFunctionalityRefs() {
        return functionalityRefs;
    }

    public void setFunctionalityRefs(Set<FunctionalityRef> functionalityRefs) {
        this.functionalityRefs = functionalityRefs;
    }

    public ContractRef getContractRef() {
        return contractRef;
    }

    public void setContractRef(ContractRef contractRef) {
        this.contractRef = contractRef;
    }

    @Override
    public MapId getMapId() {
        return BasicMapId.id().with("modelNumber", modelNumber).with("serialNumber", serialNumber);
    }

    public static MapId getMapId(String modelNumber, int serialNumber) {
        return BasicMapId.id().with("modelNumber", modelNumber).with("serialNumber", serialNumber);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o instanceof Device) {
            Device that = (Device) o;
            return new EqualsBuilder().append(modelNumber, that.modelNumber)
                                      .append(serialNumber, that.serialNumber)
                                      .append(creationDate, that.creationDate)
                                      .append(updateDate, that.updateDate)
                                      .append(status, that.status)
                                      .append(motherboardRef, that.motherboardRef)
                                      .append(brand, that.brand)
                                      .append(functionalityRefs, that.functionalityRefs)
                                      .isEquals();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(modelNumber)
                                    .append(serialNumber)
                                    .append(creationDate)
                                    .append(updateDate)
                                    .append(status)
                                    .append(motherboardRef)
                                    .append(brand)
                                    .append(functionalityRefs)
                                    .toHashCode();
    }

}
