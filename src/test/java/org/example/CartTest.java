package org.example;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


public class CartTest {
    private Cart cart;


    @Before
    public void setUp() {
        cart = new Cart();
    }

    @Test
    public void addToCart() {
        Product product = mock(Product.class);
        when(product.getName()).thenReturn("Product 1");

        cart.addToCart(product);

        assertTrue(cart.getListOfProducts().contains(product));
    }

    @Test
    public void removeFromCart() {
        Product product = mock(Product.class);
        when(product.getName()).thenReturn("Product 2");

        cart.addToCart(product);
        cart.removeFromCart(product);

        assertFalse(cart.getListOfProducts().contains(product));
    }

    @Test
    public void checkout() {
        Product product1 = mock(Product.class);
        Product product2 = mock(Product.class);

        when(product1.getName()).thenReturn("Product 3");
        when(product2.getName()).thenReturn("Product 4");

        cart.addToCart(product1);
        cart.addToCart(product2);

        cart.checkout();

        verify(product1, times(2)).getName();
        verify(product2, times(2)).getName();
    }

    @Test
    public void getOrderStatus() {
        Product product = mock(Product.class);
        when(product.getName()).thenReturn("Product 5");

        cart.addToCart(product);
        cart.checkout();
        int orderId = cart.getOrders().get(0).getOrderId();

        assertEquals("Pending", cart.getOrderStatus(orderId));
    }
}