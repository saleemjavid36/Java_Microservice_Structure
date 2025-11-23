package com.pm.patientservice.kafaka;

import com.pm.commonevent.events.PatientRegisteredEvent;
//import com.pm.patientservice.events.PatientRegisteredEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class PatientProducer {
    private final KafkaTemplate<String, PatientRegisteredEvent> kafkaTemplate;

    public PatientProducer(KafkaTemplate<String, PatientRegisteredEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendPatientRegisteredEvent(PatientRegisteredEvent event) {
        kafkaTemplate.send("patient-registered-topic", event);
        System.out.println("🔥 Event sent: " + event);
    }
}
