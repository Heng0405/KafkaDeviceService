package com.example.deviceservice.domain.model;

public abstract class DomainRefModel<DMO, DMR> extends DomainModel<DMO> {

    public abstract DMR toRef();

}
