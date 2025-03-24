# Kafka Producer Message Acknowledgement

Kafka producers only write data to the current leader broker for a partition. **A Kafka producer configuration parameter called acks** which is the number of brokers who need to acknowledge receiving the message before it is considered a successful write.

Kafka producers must also specify a level of acknowledgment acks to specify if the message must be written to a minimum number of replicas before being considered a successful write.

> The default value of acks has changed with Kafka v3.0

> if using Kafka < v3.0, acks=1

> if using Kafka >= v3.0, acks=all

## acks=0

When acks=0 producers consider messages as "written successfully" the moment the message was sent without waiting for the broker to accept it at all.

This 'fire-and-forget' approach is only useful for scenarios where it is OK to potentially lose messages or data.

If the broker goes offline or an exception happens, we won’t know and will lose data. This is useful for data where it’s okay to potentially lose messages, such as metrics collection, and produces the highest throughput setting because the network overhead is minimized.


