package com.pm.appointmentservice.kafka;

import com.google.protobuf.InvalidProtocolBufferException;
import com.pm.appointmentservice.grpc.PatientGrpcClient;
import com.pm.grpc.PatientResponse;
import com.pm.patientKafka.PatientRegisteredResponse;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
@Slf4j
public class PatientEventConsumer {
    private final KafkaTemplate<String, byte[]> retryTemplate;
    private final KafkaTemplate<String, byte[]> transactionalKafkaTemplate;
    private final PatientGrpcClient patientGrpcClient; // <-- inject client

    public PatientEventConsumer(
            @Qualifier("kafkaTemplate") KafkaTemplate<String, byte[]> retryTemplate,
            @Qualifier("transactionalKafkaTemplate") KafkaTemplate<String, byte[]> transactionalKafkaTemplate,
            PatientGrpcClient patientGrpcClient
    ) {
        this.retryTemplate = retryTemplate;
        this.transactionalKafkaTemplate = transactionalKafkaTemplate;
        this.patientGrpcClient = patientGrpcClient;
    }

    @KafkaListener(
//            topics ={
//                    "patient-registered-topic",
//                    "patient-registered-retry-topic"
//            },
//            topicPattern = "patient-registered-.*", // Matches both 'patient-registered-topic' and 'patient-registered-retry-topic'
            topicPartitions = @TopicPartition(
                    topic = "patient-registered-topic",
                    partitions = {"0"}
            ),
            groupId = "appointment-group",
            containerFactory = "patientEventListenerFactory"
    )
    public void consume(
            byte[] message,
            Acknowledgment ack,
            @Header(value = "retry-count", required = false) byte[] retryHeader,
            @Header(KafkaHeaders.OFFSET) long offset,
            @Header(KafkaHeaders.RECEIVED_PARTITION) int partition
    ) {
        try {
            log.info("📥 Received message from partition {} at offset {}  ", partition, offset);
            // **Manual Deserialization using Protobuf's parseFrom()**
            PatientRegisteredResponse event = PatientRegisteredResponse.parseFrom(message);
            log.info("📥 Received Kafka Event: {}", event);
//
            simulateBusinessLogic(event); // may throw
            // we now call the transactional handler
//            processWithTransaction(message, ack);
            ack.acknowledge(); // commit offset AFTER success
        } catch (Exception  ex) {
            Integer retryCount = retryHeader == null ? 0 : Integer.parseInt(new String(retryHeader));

            retryCount++;

            if (retryCount <= 3) {
                log.warn("⚠️ Retry attempt #{} sending back to retry-topic", retryCount);
                sendToRetry(message, retryCount);
            } else {
                log.error("❌ Moving message to DLQ after {} attempts", retryCount);
                sendToDLQ(message);
            }

            ack.acknowledge(); // prevent infinite loop
        }
    }
    private void simulateBusinessLogic(PatientRegisteredResponse event) {
        if (event.getEmail().contains("diya")) {
            throw new RuntimeException("Simulated processing failure");
        }
    }

    //    Normal Kafka guarantees at-least-once delivery, meaning duplicates can happen.
//
//    But in some workflows, duplicates are unacceptable:
//    Sending bill twice,Deducting wallet amount twice, Creating duplicate appointment record
    @Transactional
    public void processWithTransaction(byte[] message, Acknowledgment ack) {

        transactionalKafkaTemplate.executeInTransaction(operations -> {

            PatientRegisteredResponse event = null;
            try {
                event = PatientRegisteredResponse.parseFrom(message);
                log.info("🛠 Processing with transaction: {}", event);

                // 💡 Example: Create appointment exactly ONCE
                operations.send("appointment-created", event.toByteArray());

                // notify: example
                operations.send("appointment-notification", ("Email sent to " + event.getEmail()).getBytes());

                // IMPORTANT: offset commit is part of transaction
                ack.acknowledge();

            } catch (Exception e) {
                log.error("❌ Transaction execution failed. Rolling back.", e);
                throw new RuntimeException(e);
            }

            return null;
        });
    }



    private void sendToRetry(byte[] message, int retryCount) {

        // 1. Construct the ProducerRecord explicitly
        ProducerRecord<String, byte[]> record = new ProducerRecord<>(
                "patient-registered-retry-topic",
                null, // key
                message // value
        );

        // 2. Add the headers before sending
        // Protobuf (and byte[]) payloads require headers to be byte[]
        byte[] retryCountBytes = String.valueOf(retryCount).getBytes(StandardCharsets.UTF_8);
        record.headers().add(new RecordHeader("retry-count", retryCountBytes));
        // 3. Send the complete record
        retryTemplate.send(record);
    }

    private void sendToDLQ(byte[] message) {
        log.error("🚨 Message permanently moved to DLQ!");
        retryTemplate.send("patient-registered-dlq", message);
    }


}
