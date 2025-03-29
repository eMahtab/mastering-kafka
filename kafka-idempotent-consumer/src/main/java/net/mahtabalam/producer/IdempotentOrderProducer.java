package net.mahtabalam.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import net.mahtabalam.domain.Order;
import net.mahtabalam.domain.OrderStatus;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Properties;
import java.util.Random;
import java.util.UUID;

public class IdempotentOrderProducer {


    private static final String TOPIC = "orders-topic";
    private static final String BOOTSTRAP_SERVERS = "localhost:9092";

    private static final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    private static final Random random = new Random();

    public void produceOrders(int numberOfOrders) {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);

        try (KafkaProducer<String, String> producer = new KafkaProducer<>(props)) {
            for (int i = 0; i < numberOfOrders; i++) {
                String customerPrefix = String.format("%03d", random.nextInt(1000));
                String orderId = customerPrefix + "-" +
                        String.format("%07d", random.nextInt(10000000)) + "-"
                        + String.format("%07d", random.nextInt(10000000));
                Order order = new Order(orderId, UUID.randomUUID().toString(), "Product" + i, new BigDecimal(100 * (i + 1)), OrderStatus.PENDING, OffsetDateTime.now());
                String orderJson = objectMapper.writeValueAsString(order);
                ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC, orderId, orderJson);
                producer.send(record);
                System.out.println("Produced order: " + order);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
