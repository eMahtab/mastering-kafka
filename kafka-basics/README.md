# Kafka Basics

## Messages with same key :

In Apache Kafka, messages with the same key are not considered duplicates just because they share the same key. Each message is considered a separate message, even if they have the same key. However, the key does influence which partition the message is sent to.

**Kafka guarantees ordering within a partition.**

## Message Offset :

An offset in Apache Kafka is a unique identifier assigned to each message within a partition. It represents the position of the message in a partition and is used to track the order and consumption of messages. Each partition has its own offset sequence.

If a topic has multiple partitions, offsets across partitions are independent.

Once assigned, offsets do not change. If a message is deleted due to retention policies, the offset is not reused—Kafka maintains an ever-increasing offset sequence.

Kafka does not track which messages have been read—consumers must track their own progress. Consumers commit offsets.
