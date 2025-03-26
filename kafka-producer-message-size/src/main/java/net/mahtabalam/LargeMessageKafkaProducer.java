package net.mahtabalam;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Properties;
import java.util.concurrent.Future;

public class LargeMessageKafkaProducer {

    public static void main(String[] args) {
        String topic = "large-messages";

        // Configure Kafka producer
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        // Increase message size limits
        props.put("max.request.size", 5000000);  // Increase max request size to 5MB

        Producer<String, String> producer = new KafkaProducer<>(props);

        // Create a large message (2MB)
        int messageSize = 2 * 1024 * 1024;  // 2MB
        String largeMessage = "A".repeat(messageSize);

        try {
            ProducerRecord<String, String> record = new ProducerRecord<>(topic, "key1", largeMessage);
            Future<RecordMetadata> future = producer.send(record);
            RecordMetadata metadata = future.get();  // Wait for acknowledgment
            System.out.println("Message sent to topic " + metadata.topic() + " at offset " + metadata.offset());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            producer.close();
        }
    }
}
