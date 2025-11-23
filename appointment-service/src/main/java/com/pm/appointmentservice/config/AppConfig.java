package com.pm.appointmentservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.TopicPartition;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.util.backoff.FixedBackOff;

@Configuration
public class AppConfig {
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    @Bean
    public NewTopic patientRetryTopic() {
        return TopicBuilder.name("patient-registered-retry-topic")
                .build();
    }

    @Bean
    public NewTopic patientDlqTopic() {
        return TopicBuilder.name("patient-registered-dlq-topic")
                .build();
    }

    @Bean
    public DefaultErrorHandler errorHandler(KafkaTemplate<String, Object> template) {

        // After 3 attempts → go to DLQ
        DeadLetterPublishingRecoverer recoverer =
                new DeadLetterPublishingRecoverer(template,
                        (record, ex) -> new TopicPartition("patient-registered-dlq-topic", record.partition()));

        var backOff = new FixedBackOff(5000L, 2); // retry 2 times, delay 5 sec

        return new DefaultErrorHandler(recoverer, backOff);
    }


}
