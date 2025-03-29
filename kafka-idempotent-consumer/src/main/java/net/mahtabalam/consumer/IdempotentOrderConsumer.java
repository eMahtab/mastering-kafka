package net.mahtabalam.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import net.mahtabalam.db.OrderIdempotenceService;
import net.mahtabalam.domain.Order;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class IdempotentOrderConsumer {

    private static final String TOPIC = "orders-topic";
    private static final String BOOTSTRAP_SERVERS = "localhost:9092";
    private static final String GROUP_ID = "order-consumer-group";

    private static final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    private final OrderIdempotenceService orderIdempotenceService;

    public IdempotentOrderConsumer(OrderIdempotenceService orderIdempotenceService) {
        this.orderIdempotenceService = orderIdempotenceService;
    }

    public void consumeOrders() {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        try (KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props)) {
            consumer.subscribe(Collections.singletonList(TOPIC));
            System.out.println("Subscribed to topic: " + TOPIC);

            while (true) {

                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                System.out.println("Polled " + records.count() + " records");
                for (ConsumerRecord<String, String> record : records) {
                    try {
                        Order order = objectMapper.readValue(record.value(), Order.class);
                        System.out.println("Checking order: " + order.getOrderId() + ", processed: " + orderIdempotenceService.isOrderProcessed(order.getOrderId()));
                        if (!orderIdempotenceService.isOrderProcessed(order.getOrderId())) {
                            processOrder(order);
                            orderIdempotenceService.markOrderProcessed(order.getOrderId());
                            consumer.commitSync();
                            System.out.println("Committed offset for order: " + order.getOrderId());
                        } else {
                            System.out.println("Order already processed: " + order.getOrderId());
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private void processOrder(Order order) {
        System.out.println("Processing order: " + order);
        // Simulate order processing logic
    }
}
