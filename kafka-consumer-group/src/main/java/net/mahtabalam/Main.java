package net.mahtabalam;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;

import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    private static final String TOPIC = "test-topic";
    private static final String BOOTSTRAP_SERVERS = "localhost:9092";
    private static final int NUMBER_OF_PARTITIONS = 1000;
    private static final int NUMBER_OF_CONSUMERS = 250;

    public static void main(String[] args) {
        createTopic();
        new Thread(Producer::startProducer).start();

        ExecutorService executorService = Executors.newFixedThreadPool(NUMBER_OF_CONSUMERS);
        for (int i = 1; i <= NUMBER_OF_CONSUMERS; i++) {
            executorService.submit(Consumer::startConsumer);
        }
    }

    private static void createTopic() {
        Properties config = new Properties();
        config.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);

        try (AdminClient adminClient = AdminClient.create(config)) {
            NewTopic newTopic = new NewTopic(TOPIC, NUMBER_OF_PARTITIONS, (short) 1);
            adminClient.createTopics(Collections.singletonList(newTopic)).all().get();
            System.out.println("Topic created successfully!");
        } catch (Exception e) {
            System.out.println("Topic creation failed: " + e.getMessage());
        }
    }

}
