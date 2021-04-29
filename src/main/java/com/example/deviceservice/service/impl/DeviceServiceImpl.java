package com.example.deviceservice.service.impl;

import com.example.deviceservice.domain.model.ContractRef;
import com.example.deviceservice.domain.model.Device;
import com.example.deviceservice.domain.model.HasContractRef;
import com.example.deviceservice.domain.repository.DeviceRepository;
import com.example.deviceservice.service.DeviceService;
import com.example.dtoutils.utils.ErrorCode;

import java.time.Instant;
import java.util.Optional;

public class DeviceServiceImpl implements DeviceService {

    private final DeviceRepository deviceRepository;

    public DeviceServiceImpl(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    @Override
    public Device findById(String modelNumber, int serialNumber) {
        return deviceRepository.findById(Device.getMapId(modelNumber, serialNumber)).orElse(null);
    }

    @Override
    public ErrorCode updateDevice(Device device) {
        ErrorCode errorCode = ErrorCode.UNCHANGED;
        Device toUpdate = this.findById(device.getModelNumber(), device.getSerialNumber());
        if (null != toUpdate) {
            boolean updateFunctionalityRef = Optional.of(device)
                                                     .map(Device::getFunctionalityRefs)
                                                     .filter(f -> !f.isEmpty())
                                                     .map(f -> !f.equals(toUpdate.getFunctionalityRefs()))
                                                     .orElse(Boolean.FALSE);
            boolean updateContractRef = Optional.of(device)
                                                .map(Device::getContractRef)
                                                .map(c -> !c.equals(toUpdate.getContractRef()))
                                                .orElse(false);
            if (updateFunctionalityRef || updateContractRef) {
                errorCode = ErrorCode.SUCCESS;
                device.setUpdateDate(Instant.now());
            }
            deviceRepository.insert(device);
        } else {
            errorCode = ErrorCode.UNKNOWN_RECORD;
        }
        return errorCode;
    }

    @Override
    public ContractRef getContract(String modelNumber, int serialNumber) {
        return deviceRepository.findContractRefByModelNumberAndSerialNumber(modelNumber, serialNumber)
                               .map(HasContractRef::getContractRef)
                               .orElse(null);
    }
}
