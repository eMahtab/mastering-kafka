package net.mahtabalam.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

public class Order implements Serializable {
    private String orderId;
    private String transactionId;
    private String productName;
    private BigDecimal amount;
    private OrderStatus status;
    private OffsetDateTime createdAt;

    public Order() {}

    public Order(String orderId, String transactionId, String productName, BigDecimal amount, OrderStatus status, OffsetDateTime createdAt) {
        this.orderId = orderId;
        this.transactionId = transactionId;
        this.productName = productName;
        this.amount = amount;
        this.status = status;
        this.createdAt = createdAt;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", transactionId='" + transactionId + '\'' +
                ", productName='" + productName + '\'' +
                ", amount=" + amount +
                ", status=" + status +
                ", createdAt=" + createdAt +
                '}';
    }
}
