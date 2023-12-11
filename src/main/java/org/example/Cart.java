package org.example;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private final List<Order> orders = new ArrayList<>();
    private final List<Product> listOfProducts;
    public Cart () {
        this.listOfProducts = new ArrayList<>();
    }

    public void addToCart (Product product) {
        listOfProducts.add(product);
        System.out.println(product.getName() + " has been added to the cart.");
    }

    public void removeFromCart (Product product) {
        if (listOfProducts.remove(product)) {
            System.out.println(product.getName() + " has been removed from the cart.");
        } else {
            System.out.println("Product not found in the cart.");
        }
    }

    public void checkout() {
        if (listOfProducts.isEmpty()) {
            System.out.println("The cart is empty. Cannot create an order.");
            return;
        }

        StringBuilder productsInfo = new StringBuilder();
        for (Product product : listOfProducts) {
            productsInfo.append("Id: ").append(product.getId()).append(", ");
            productsInfo.append("Name: ").append(product.getName()).append(", ");
            productsInfo.append("Price: ").append(product.getPrice()).append("\n");
        }


        Order order = new Order(generateOrderId(), productsInfo.toString(), "Pending");
        orders.add(order);


        listOfProducts.clear();

        System.out.println("Order created successfully:");
        System.out.println("Order ID: " + order.getOrderId());
        System.out.println("Products: \n" + order.getProducts());
        System.out.println("Status: " + order.getStatus());
    }

    private static int orderIdCounter = 1;
    private int generateOrderId() {
        int orderId = orderIdCounter;
        orderIdCounter++;
        return orderId;
    }

    public String getOrderStatus(int orderId) {
        for (Order order : orders) {
            if (order.getOrderId() == orderId) {
                return order.getStatus();
            }
        }
        return "Order not found.";
    }

    public List<Product> getListOfProducts() {
        return listOfProducts;
    }

    public List<Order> getOrders() {
        return orders;
    }
}