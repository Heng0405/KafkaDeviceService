package com.example.deviceservice.config;

import com.example.device.kafka.config.DeviceFindQueryDeserializer;
import com.example.device.kafka.config.DeviceFindReplySerializer;
import com.example.device.msg.dto.DeviceFindReply;
import com.example.deviceservice.Application;
import com.example.dtoservices.config.DeviceKafkaContainerFactoryBuilder;
import com.example.dtoservices.config.DeviceKafkaTemplateBuilder;
import com.example.dtoutils.kafka.KafkaConstants;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.autoconfigure.kafka.ConcurrentKafkaListenerContainerFactoryConfigurer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.SchedulingTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.UUID;

@Configuration
@EnableKafka
public class KafkaConfig {

    public static final String DEVICE_FIND_QUERY_GROUP_ID = KafkaConstants.DEVICE_FIND_QUERY_GROUP_ID_PREFIX + Application.COMPONENT_NAME;

    private final KafkaProperties kafkaProperties;

    public KafkaConfig(KafkaProperties kafkaProperties) {
        this.kafkaProperties = kafkaProperties;
    }

    @Bean
    public KafkaTemplate<UUID, byte[]> kafkaTemplate() {
        return new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(kafkaProperties.buildProducerProperties()));
    }

    @Bean
    public KafkaTemplate<UUID, DeviceFindReply> deviceFindReplyKafkaTemplate() {
        return DeviceKafkaTemplateBuilder.create().withKafkaProperties(kafkaProperties).withValueSerializerClass(
                DeviceFindReplySerializer.class).build();
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<Object, Object> deviceFindQueryContainerFactory(
            ConcurrentKafkaListenerContainerFactoryConfigurer configurer) {
        return DeviceKafkaContainerFactoryBuilder.create(configurer)
                                                 .withKafkaProperties(kafkaProperties)
                                                 .withGroupId(DEVICE_FIND_QUERY_GROUP_ID)
                                                 .withValueDeserializerClass(DeviceFindQueryDeserializer.class)
                                                 .build();
    }

    @Bean
    public NewTopic deviceFindReplyTopic() {
        return new NewTopic(KafkaConstants.DEVICE_FIND_REPLY_TOPIC, 1, (short) 1);
    }

    @Bean
    public SchedulingTaskExecutor deviceRequestExecutor() {
        return new ThreadPoolTaskExecutor();
    }
}
