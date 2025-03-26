# Producer Message Size

The default message size that can be sent to Kafka is 1 MB.

But we can also send large messages (greater than 1MB and less than 10 MB) to Kafka, **by changing the Kafka topic and Producer settings.**

```java
java.util.concurrent.ExecutionException: org.apache.kafka.common.errors.RecordTooLargeException: The message is 2097244 bytes when serialized which is larger than 1048576, which is the value of the max.request.size configuration.
at org.apache.kafka.clients.producer.KafkaProducer$FutureFailure.<init>(KafkaProducer.java:1427)
at org.apache.kafka.clients.producer.KafkaProducer.doSend(KafkaProducer.java:1069)
at org.apache.kafka.clients.producer.KafkaProducer.send(KafkaProducer.java:947)
at org.apache.kafka.clients.producer.KafkaProducer.send(KafkaProducer.java:832)
at net.mahtabalam.LargeMessageKafkaProducer.main(LargeMessageKafkaProducer.java:33)
Caused by: org.apache.kafka.common.errors.RecordTooLargeException: The message is 2097244 bytes when serialized which is larger than 1048576, which is the value of the max.request.size configuration.
```

## Producer Side

You must change the property max.request.size at producer side to ensure large messages can be sent.

```java
props.put("max.request.size", 5000000);  // Increase max request size to 5MB
```


```java
java.util.concurrent.ExecutionException: org.apache.kafka.common.errors.RecordTooLargeException: The request included a message larger than the max message size the server will accept.
	at org.apache.kafka.clients.producer.internals.FutureRecordMetadata.valueOrError(FutureRecordMetadata.java:97)
	at org.apache.kafka.clients.producer.internals.FutureRecordMetadata.get(FutureRecordMetadata.java:65)
	at org.apache.kafka.clients.producer.internals.FutureRecordMetadata.get(FutureRecordMetadata.java:30)
	at net.mahtabalam.LargeMessageKafkaProducer.main(LargeMessageKafkaProducer.java:34)
Caused by: org.apache.kafka.common.errors.RecordTooLargeException: The request included a message larger than the max message size the server will accept.
```


## Broker Side :

> It is recommended to leave the max message size default for the Kafka brokers and **only override this at the topic level through topic-level configurations.**

### Updating  max.message.bytes for specific topic

```
kafka-configs.sh --bootstrap-server localhost:9092 --entity-type topics --entity-name large-messages --alter --add-config max.message.bytes=5000000

Completed updating config for topic large-messages.

```

