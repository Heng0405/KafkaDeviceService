package com.example.deviceservice.msg;

import com.example.device.msg.dto.DeviceFindQuery;
import com.example.device.msg.dto.DeviceFindReply;
import com.example.dtoservices.kafka.DeviceKafkaConsumer;
import com.example.dtoservices.kafka.DeviceKafkaProducer;
import com.example.dtoservices.msg.impl.RequestManager;

public interface DeviceRequestManager extends RequestManager<DeviceFindQuery, DeviceFindReply>,
                                              DeviceKafkaProducer<DeviceFindReply>,
                                              DeviceKafkaConsumer<DeviceFindQuery> {
}
