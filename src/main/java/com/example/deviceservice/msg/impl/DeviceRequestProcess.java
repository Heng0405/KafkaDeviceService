package com.example.deviceservice.msg.impl;

import com.example.device.msg.dto.*;
import com.example.deviceservice.Application;
import com.example.deviceservice.domain.model.Device;
import com.example.deviceservice.service.DeviceService;
import com.example.dtoservices.msg.impl.RequestCallback;
import com.example.dtoservices.msg.impl.RequestProcess;
import com.example.dtoutils.utils.ErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Component
@Scope("prototype")
public class DeviceRequestProcess implements RequestProcess<DeviceFindQuery, DeviceFindReply> {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceRequestProcess.class);

    private final AtomicBoolean running = new AtomicBoolean(true);

    private final DeviceService deviceService;

    private UUID processId;
    private DeviceFindQuery object;
    private RequestCallback<DeviceFindQuery, DeviceFindReply> doFirst;
    private RequestCallback<DeviceFindQuery, DeviceFindReply> doLast;

    public DeviceRequestProcess(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @Override
    public void setProcessId(UUID uuid) {
        this.processId = uuid;
    }

    @Override
    public void setRequest(DeviceFindQuery deviceFindQuery) {
        this.object = deviceFindQuery;
    }

    @Override
    public void doFirst(RequestCallback<DeviceFindQuery, DeviceFindReply> requestCallback) {
        this.doFirst = doFirst;
    }

    @Override
    public void doLast(RequestCallback<DeviceFindQuery, DeviceFindReply> requestCallback) {
        this.doLast =doLast;
    }

    //TODO
    @Override
    public DeviceFindReply call() throws Exception {
        if (doFirst != null) {
            doFirst.invoke(processId, object, null);
        }
        DeviceFindReply.Builder builder = DeviceFindReply.newBuilder();
        ErrorCode execCode = ErrorCode.SUCCESS;
        try{
            Device device = deviceService.findById(object.getModelNumber(), object.getSerialNumber());
            DeviceDto.Builder deviceDto = DeviceDto.newBuilder()
                                           .setId(DeviceIdDto.newBuilder()
                                                             .setModelNumber(device.getModelNumber())
                                                             .setSerialNumber(device.getSerialNumber()))
                                           .setCreationDate(device.getCreationDate().toEpochMilli())
                                           .setBrand(device.getBrand())
                                           .setMotherboard(MotherboardRefDto.newBuilder()
                                                                            .setKuid(device.getMotherboardRef()
                                                                                           .getKuid())
                                                                            .setMacAddressValue(device.getMotherboardRef()
                                                                                                      .getMacAddressValue())
                                                                            .setModelKind(ModelKindRefDto.newBuilder()
                                                                                                         .setName(device.getMotherboardRef()
                                                                                                                        .getModelKindRef()
                                                                                                                        .getName())
                                                                                                         .setDeviceKind(
                                                                                                                 device.getMotherboardRef()
                                                                                                                       .getModelKindRef()
                                                                                                                       .getDeviceKind())))
                                           .setStatus(device.getStatus().name())
                                           .setUpdateDate(Optional.ofNullable(device.getUpdateDate())
                                                                  .map(Instant::toEpochMilli)
                                                                  .orElse(0l))
                                           .setLastConnectionDate(Optional.ofNullable(device.getLastConnectionDate())
                                                                          .map(Instant::toEpochMilli)
                                                                          .orElse(0L))
                                           .addAllFunctionalities(Optional.ofNullable(device.getFunctionalityRefs())
                                                                                      .orElse(Collections.emptySet())
                                                                                      .stream()
                                                                                      .map(f -> FunctionalityRefDto.newBuilder()
                                                                                                                   .setName(f.getName())
                                                                                                                   .setKind(f.getKind())
                                                                                                                   .build())
                                                                                      .collect(Collectors.toSet()));
            if (device.getContractRef() != null) {
                deviceDto.setContract(ContractRefDto.newBuilder()
                                                    .setNumber(device.getContractRef().getNumber())
                                                    .setStartDate(device.getContractRef().getStartDate().toEpochMilli())
                                                    .setEndDate(Optional.ofNullable(device.getContractRef().getEndDate())
                                                                        .map(Instant::toEpochMilli)
                                                                        .orElse(0L))
                                                    .addAllActivatedRights(Optional.ofNullable(device.getContractRef().getActivatedRights())
                                                                                   .orElse(Collections.emptySet())));
            }
            builder.setDevice(deviceDto);
        } catch (Exception e) {
            execCode = ErrorCode.OTHER;
        }
        DeviceFindReply result = builder.setExecCode(execCode.name()).build();
        if (doLast != null) {
            doLast.invoke(processId, object, result);
        }
        running.set(false);
        LOGGER.trace("[{}] process [{}] [device] [find] [query] [finished]: modelNumber={}, serialNumber={}, time={}",
                     Application.COMPONENT_NAME, this.processId, this.object.getModelNumber(), this.object.getSerialNumber(), System.currentTimeMillis());
        return result;
    }

}























