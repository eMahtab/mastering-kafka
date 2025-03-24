# Idempotent Kafka Producer

## Problem with Retries

Retrying to send a failed message often includes a small risk that both messages were successfully written to the broker, leading to duplicates. This can happen as illustrated below.

1. Kafka producer sends a message to Kafka

2. The message was successfully written and replicated

3. Network issues prevented the broker acknowledgment from reaching the producer

4. The producer will treat the lack of acknowledgment as a temporary network issue and will retry sending the message (since it can’t know that it was received).

5. In that case, the broker will end up having the same message twice.

!["Duplicate message scenario"](images/idempotent-producer/duplicate-message.jpg)

