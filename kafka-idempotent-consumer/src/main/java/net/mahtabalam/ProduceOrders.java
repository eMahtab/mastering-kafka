package net.mahtabalam;

import net.mahtabalam.producer.IdempotentOrderProducer;

public class ProduceOrders {

    public static void main(String[] args) {
        IdempotentOrderProducer producer = new IdempotentOrderProducer();

        producer.produceOrders(10000);
    }
}