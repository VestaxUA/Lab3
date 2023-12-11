package org.example;

public class Order {
    private final int orderId;
    private final String products;
    private final String status;

    public Order (int orderId, String products, String status) {
        this.orderId = orderId;
        this.products = products;
        this.status = status;
    }

    public int getOrderId() {
        return orderId;
    }

    public String getProducts() {
        return products;
    }

    public String getStatus() {
        return status;
    }
}