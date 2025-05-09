## Message Offset :

An offset in Apache Kafka is a unique identifier assigned to each message within a partition. It represents the position of the message in a partition and is used to track the order and consumption of messages. Each partition has its own offset sequence.

If a topic has multiple partitions, offsets across partitions are independent.

Once assigned, offsets do not change. If a message is deleted due to retention policies, the offset is not reused—Kafka maintains an ever-increasing offset sequence.

Kafka does not track which messages have been read—consumers must track their own progress. Consumers commit offsets.

## Why Are Offsets Important?

1. Ensure Message Ordering (within a partition).

2. Allow Consumers to Resume Processing from where they left off.

3. Support Replayability: Consumers can reprocess old messages by seeking to previous offsets.

4. Help in Fault Recovery: If a consumer fails, another can take over from the last committed offset.