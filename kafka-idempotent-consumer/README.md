# Kafka Idempotent Consumer

## Project :

!["Idempotent Consumer"](images/idempotent-consumer.jpg)

## Project Dependencies :
```xml
<dependencies>

        <dependency>
            <groupId>org.apache.kafka</groupId>
            <artifactId>kafka-clients</artifactId>
            <version>3.6.0</version>
        </dependency>

        <dependency>
            <groupId>org.postgresql</groupId>
            <artifactId>postgresql</artifactId>
            <version>42.7.5</version>
        </dependency>

        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-databind</artifactId>
            <version>2.18.2</version>
        </dependency>

        <dependency>
            <groupId>com.fasterxml.jackson.datatype</groupId>
            <artifactId>jackson-datatype-jsr310</artifactId>
            <version>2.16.0</version>
        </dependency>

    </dependencies>
```
## Create the processed_order_messages table under orders db:

We are using processed_order_messages to store the order ids once the consumer processes it.

```sql
CREATE TABLE processed_order_messages (
    order_id VARCHAR(255) PRIMARY KEY
);
```

## Run ProduceOrders :

Run the ProduceOrders class , it will publish 10,000 orders in orders-topic.

```java
public class ProduceOrders {

    public static void main(String[] args) {
        IdempotentOrderProducer producer = new IdempotentOrderProducer();

        producer.produceOrders(10000);
    }
}
```

## Verify orders in orders-topic :

!["Orders topic"](images/orders-topic.jpg)

## Run ConsumeOrders :

Run the ConsumeOrders class, it will consume orders one by one, if an order is already processed (order id already exist in processed_order_messages table), it won't be processed again by consumer.

```java
public class ConsumeOrders {

    public static void main(String[] args) {

        OrderIdempotenceService orderIdempotenceService = new OrderIdempotenceService();
        IdempotentOrderConsumer consumer = new IdempotentOrderConsumer(orderIdempotenceService);

        consumer.consumeOrders();
    }
}
```


## Verify order ids in processed_order_messages table :

!["Processed Order Messages Table"](images/processed_order_messages.jpg)

