package com.pm.appointmentservice.kafka;

import com.pm.patientKafka.PatientRegisteredResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DeadLetterConsumer {
    @KafkaListener(
            topics = "patient-registered-dlq",
            groupId = "appointment-group"
    )
    public void consume(byte[] message) throws Exception {

        PatientRegisteredResponse event =
                PatientRegisteredResponse.parseFrom(message);

        log.error("❌ Permanently failed message moved to DLQ: {}", event);
    }
}
