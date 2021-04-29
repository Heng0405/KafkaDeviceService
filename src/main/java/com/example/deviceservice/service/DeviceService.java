package com.example.deviceservice.service;

import com.example.deviceservice.domain.model.ContractRef;
import com.example.deviceservice.domain.model.Device;
import com.example.dtoutils.utils.ErrorCode;

public interface DeviceService {

    Device findById(String modelNumber, int serialNumber);

    ErrorCode updateDevice(Device device);

    ContractRef getContract(String modelNumber, int serialNumber);
}
