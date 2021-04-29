package com.example.deviceservice.domain.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.data.cassandra.core.mapping.BasicMapId;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.MapId;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import java.time.Instant;
import java.util.Set;

@UserDefinedType(ContractRef.TYPE)
public class ContractRef extends DomainModel<ContractRef> {

    public static final String TYPE = "contract_ref";
    public static final String COLUMN_NUMBER = "number";
    public static final String COLUMN_START_DATE = "start_date";
    public static final String COLUMN_END_DATE = "end_date";
    public static final String COLUMN_RIGHTS = "activated_rights";

    @Column(COLUMN_NUMBER)
    private String number;
    @Column(COLUMN_START_DATE)
    private Instant startDate;
    @Column(COLUMN_END_DATE)
    private Instant endDate;
    @Column(COLUMN_RIGHTS)
    private Set<String> activatedRights;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public Set<String> getActivatedRights() {
        return activatedRights;
    }

    public void setActivatedRights(Set<String> activatedRights) {
        this.activatedRights = activatedRights;
    }

    @Override
    public MapId getMapId() {
        return BasicMapId.id().with("number", number);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o instanceof ContractRef) {
            ContractRef that = (ContractRef) o;
            return new EqualsBuilder().append(number, that.number)
                                      .append(startDate, that.startDate)
                                      .append(endDate, that.endDate)
                                      .append(activatedRights,that.activatedRights)
                                      .isEquals();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(number)
                                    .append(startDate)
                                    .append(endDate)
                                    .append(activatedRights)
                                    .toHashCode();
    }
}
