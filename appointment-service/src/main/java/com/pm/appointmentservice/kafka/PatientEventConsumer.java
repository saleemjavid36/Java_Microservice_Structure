package com.pm.appointmentservice.kafka;

import com.pm.commonevent.events.PatientRegisteredEvent;
//import com.pm.appointmentservice.events.PatientRegisteredEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PatientEventConsumer {
    private final KafkaTemplate<String, PatientRegisteredEvent> kafkaTemplate;

    public PatientEventConsumer(KafkaTemplate<String, PatientRegisteredEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = "patient-registered-topic", groupId = "appointment-group")
    public void consume(PatientRegisteredEvent event) {
        try{
            log.info("📥 Received Kafka Event: {}", event);
        }catch (Exception ex) {
            // retry
            kafkaTemplate.send("patient-registered-retry-topic", event);
            System.out.println("Retrying event...");
        }

    }

    @KafkaListener(topics = "patient-registered-retry-topic", groupId = "appointment-group")
    public void consumeRetry(PatientRegisteredEvent event) {
        try {
            log.info("📥 Received Kafka Event: {}", event);
        } catch (Exception ex) {
            kafkaTemplate.send("patient-registered-dlq-topic", event);
            System.out.println("❌ Sending to DLQ: " + event);
        }
    }
}
