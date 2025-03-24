# Idempotent Kafka Producer

## Problem with Retries

Retrying to send a failed message often includes a small risk that both messages were successfully written to the broker, leading to duplicates. This can happen as illustrated below.

1. Kafka producer sends a message to Kafka

2. The message was successfully written and replicated

3. Network issues prevented the broker acknowledgment from reaching the producer

4. The producer will treat the lack of acknowledgment as a temporary network issue and will retry sending the message (since it can’t know that it was received).

5. In that case, the broker will end up having the same message twice.

!["Duplicate message scenario"](images/idempotent-producer/duplicate-message.jpg)

## Kafka Idempotent Producer

> Version Availability
> Producer idempotence can be enabled for Kafka versions >= 0.11

Producer idempotence ensures that duplicates are not introduced due to unexpected retries.

### How does Idempotent Producer work internally? 

When **enable.idempotence** is set to true, **each producer gets assigned a Producer Id (PID) and the PIDis included every time a producer sends messages to a broker**. Additionally, each message gets a monotonically increasing sequence number (different from the offset - used only for protocol purposes). A separate sequence is maintained for each topic partition that a producer sends messages to. On the broker side, on a per partition basis, it keeps track of the largest PID-Sequence Number combination that is successfully written. When a lower sequence number is received, it is discarded.

> How should Kafka producer idempotence be enabled?
> Now the Default
> **Starting with Kafka 3.0, producer are by default having enable.idempotence=true and acks=all**
