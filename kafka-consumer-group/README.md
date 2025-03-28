# Kafka Consumer Group

https://github.com/eMahtab/mastering-kafka/blob/main/kafka-basics/consumer-group.md

## Benefits of Kafka Consumer Group over individual consumers :

### Scalability

If you add more partitions or consumers, Kafka automatically balances the workload in a consumer group.

With individual consumers, you must manually distribute the workload.

### Fault Tolerance

If one consumer in a group fails, Kafka redistributes its partitions to the remaining consumers, ensuring continuous processing.

