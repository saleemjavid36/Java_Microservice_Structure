package com.pm.appointmentservice.config;

import com.pm.patientKafka.PatientRegisteredResponse;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.util.backoff.ExponentialBackOff;
import org.apache.kafka.streams.*;
import org.apache.kafka.streams.kstream.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
public class KafkaConsumerConfig {
    @Bean
    public ConsumerFactory<String, byte[]> patientEventConsumerFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9094");
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "appointment-group");
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ByteArrayDeserializer.class);

        return new DefaultKafkaConsumerFactory<>(config);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, byte[]> patientEventListenerFactory(
            ConsumerFactory<String, byte[]> consumerFactory,
            KafkaTemplate<String, byte[]> kafkaTemplate
    ) {

        ConcurrentKafkaListenerContainerFactory<String, byte[]> factory =
                new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(patientEventConsumerFactory());
//        Retry + DLQ Handler Config (Kafka Builtin Retry block)
//        ---> If you want this built in only instead of manual uncomment it from here and
//        ---> comment in Consumer logic
//        var backoff = new ExponentialBackOff(1000, 2.0);
//        backoff.setMaxInterval(10000);
//
//        factory.setCommonErrorHandler(
//                new DefaultErrorHandler(
//                        new DeadLetterPublishingRecoverer(kafkaTemplate, (rec, ex) ->
//                                new TopicPartition("patient-registered-dlq", rec.partition())),
//                        backoff
//                )
//        );

//        If you want customr retry policy to run Below code
        // ⛔️ Disable retry and DLQ handling from Spring
        DefaultErrorHandler noRetryHandler = new DefaultErrorHandler((record, ex) -> {});
        noRetryHandler.setRetryListeners((r,e,attempt)->{}); // remove backoff logging
        factory.setCommonErrorHandler(noRetryHandler);
//        Below  we are telling spring to set manual offset
//        That tells Spring:
//         🧠 “Don't auto commit — wait for ack.acknowledge()."
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);

        return factory;
    }

//    Producers Block of code
    @Bean
    public ProducerFactory<String, byte[]> patientEventProducerFactory() {
        Map<String, Object> config = new HashMap<>();

        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9094");
        // Note: Serializers for producer
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ByteArraySerializer.class);

        // Enable Idempotence + Transactions
        config.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);
        config.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, "appointment-service-tx");
        return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean
    public KafkaTemplate<String, byte[]> kafkaTemplate() {
        // Pass the ProducerFactory here
        return new KafkaTemplate<>(patientEventProducerFactory());
    }
    // more advance topics
    @Bean
    public KafkaTemplate<String, byte[]> transactionalKafkaTemplate() {
        KafkaTemplate<String, byte[]> template = new KafkaTemplate<>(patientEventProducerFactory());
        template.setTransactionIdPrefix("appointment-service-tx-");
        return template;
    }
    @Bean
    public KafkaStreams enrichedAppointmentStream() {

        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "appointment-streams-app");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9094");
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.StringSerde.class);

        StreamsBuilder builder = new StreamsBuilder();

        // Patient TABLE (stateful)
        KTable<String, String> patientTable = builder.table("patient-topic");

        // Appointment STREAM (stateless)
        KStream<String, String> appointmentStream = builder.stream("appointment-topic");

        // JOIN
        KStream<String, String> enrichedStream = appointmentStream.join(
                patientTable,
                (appointment, patient) -> appointment + " | Patient: " + patient
        );

        // Output enriched messages
        enrichedStream.to("enriched-appointments-topic");

        KafkaStreams streams = new KafkaStreams(builder.build(), props);
        streams.start();

        System.out.println("🔥 Kafka Streams Started: appointment-streams-app");

        return streams;
    }



}
