package com.pm.patientservice.kafaka;

import com.pm.patientKafka.PatientRegisteredResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PatientProducer {
    private final KafkaTemplate<String, byte[]> kafkaTemplate;

    public PatientProducer(KafkaTemplate<String, byte[]> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendPatientRegisteredEvent(PatientRegisteredResponse event) {
        byte[] bytes = event.toByteArray(); // protobuf serialize

        // key ensures ordering per patient
        String key = String.valueOf(event.getPatientId());

        kafkaTemplate.send("patient-registered-topic", key, bytes)
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        log.error("❌ Kafka send failed for event: {}", event, ex);
                    } else {
                        log.info(
                                "🚀 Sent event with key={} partition={}",
                                key, result.getRecordMetadata().partition()
                        );
                    }
                });
    }
}
