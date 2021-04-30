package com.example.deviceservice.msg.impl;

import com.example.device.msg.dto.DeviceFindQuery;
import com.example.device.msg.dto.DeviceFindReply;
import com.example.deviceservice.Application;
import com.example.deviceservice.msg.DeviceRequestManager;
import com.example.dtoservices.msg.impl.AbstractRequestManager;
import com.example.dtoutils.kafka.KafkaConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.scheduling.SchedulingTaskExecutor;

import java.util.UUID;

public class DeviceRequestManagerImpl extends AbstractRequestManager<DeviceFindQuery, DeviceFindReply, DeviceRequestProcess> implements DeviceRequestManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceRequestManagerImpl.class);

    private final KafkaTemplate<UUID, DeviceFindReply> kafkaTemplate;

    public DeviceRequestManagerImpl(final SchedulingTaskExecutor deviceRequestExecutor, KafkaTemplate<UUID, DeviceFindReply> kafkaTemplate) {
        super(deviceRequestExecutor);
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = KafkaConstants.DEVICE_FIND_QUERY_TOPIC, containerFactory = "deviceFindQueryContainerFactory")
    public void listen(@Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) UUID processId, @Payload DeviceFindQuery data) {
        try {
            LOGGER.trace("[{}] process [{}] [device] [find] [query] [received]: modelNumber={}, serialNumber={}",
                         Application.COMPONENT_NAME, processId, data.getModelNumber(), data.getSerialNumber());
            submit(processId, data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public KafkaTemplate<UUID, DeviceFindReply> getKafkaTemplate() {
        return this.kafkaTemplate;
    }


    @Override
    protected void beforeSubmit(DeviceRequestProcess process) {
        process.doLast((processId, object, result) -> {
            this.send(KafkaConstants.DEVICE_FIND_REPLY_TOPIC, processId, result);
            LOGGER.trace("[{}] process [{}] [device] [find] [reply] [sent]: modelNumber={}, serialNumber = {}",
                         Application.COMPONENT_NAME, processId, object.getModelNumber(), object.getSerialNumber());
        });
    }

    @Override
    protected DeviceRequestProcess createProcess() {
        return null;
    }
}
