package net.mahtabalam;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class Producer {

    public static void main(String[] args) {
        // Kafka configuration
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        // Create producer
        try (KafkaProducer<String, String> producer = new KafkaProducer<>(props)) {
            String topic = "simple-producer-consumer";

            // Send 10 sample messages
            for (int i = 0; i < 10; i++) {
                String key = "key-" + i;
                String value = "Hello Kafka " + i;

                ProducerRecord<String, String> record =
                        new ProducerRecord<>(topic, key, value);

                // Asynchronous send with callback
                producer.send(record, (metadata, exception) -> {
                    if (exception == null) {
                        System.out.printf("Sent message: key=%s, value=%s, partition=%d, offset=%d%n",
                                key, value, metadata.partition(), metadata.offset());
                    } else {
                        System.err.println("Error sending message: " + exception.getMessage());
                    }
                });
            }

            // Flush data
            producer.flush();
            System.out.println("Messages sent successfully");

        } catch (Exception e) {
            System.err.println("Error in Kafka producer: " + e.getMessage());
        }
    }
}