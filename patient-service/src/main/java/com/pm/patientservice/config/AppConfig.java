package com.pm.patientservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class AppConfig {
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    @Bean
    public NewTopic patientTopic(){
        return TopicBuilder.name("patient-registered-tpoic")
                .partitions(3)
                .replicas(1)
                .build();
    }
}
