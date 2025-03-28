# Kafka Consumer Group

https://github.com/eMahtab/mastering-kafka/blob/main/kafka-basics/consumer-group.md

## Benefits of Kafka Consumer Group over individual consumers :

### Scalability

If you add more partitions or consumers, Kafka automatically balances the workload in a consumer group.

With individual consumers, you must manually distribute the workload.

### Fault Tolerance

If one consumer in a group fails, Kafka redistributes its partitions to the remaining consumers, ensuring continuous processing.

### Topic with 1000 Partitions

!["Test Topic with 1000 Partitions"](test-topic.jpg)

### Consumer group with 250 Consumers

!["Consumer group with 250 Consumers"](test-consumer-group.jpg)
