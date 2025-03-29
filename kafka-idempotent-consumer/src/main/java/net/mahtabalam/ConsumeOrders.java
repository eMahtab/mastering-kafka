package net.mahtabalam;

import net.mahtabalam.consumer.IdempotentOrderConsumer;
import net.mahtabalam.db.OrderIdempotenceService;

public class ConsumeOrders {

    public static void main(String[] args) {

        OrderIdempotenceService orderIdempotenceService = new OrderIdempotenceService();
        IdempotentOrderConsumer consumer = new IdempotentOrderConsumer(orderIdempotenceService);

        consumer.consumeOrders();
    }
}
