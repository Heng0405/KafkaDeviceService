package com.example.deviceservice.domain.repository;

import com.example.deviceservice.domain.model.Device;
import com.example.deviceservice.domain.model.HasContractRef;
import org.springframework.data.cassandra.repository.MapIdCassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeviceRepository extends MapIdCassandraRepository<Device> {
    Optional<HasContractRef> findContractRefByModelNumberAndSerialNumber(String modelNumber, int serialNumber);
}
