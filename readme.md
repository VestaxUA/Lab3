
![Autumn Leaves](./autumn.jpg)

# Лабораторна робота 3
## виконав : студент групи ПД-32 Ткачищен Валентин
***
### План роботи:

1. Створіть базову систему електронної комерції, яка складається з сутностей Product, Cart та Order. Система повинна дозволяти користувачам:
   - Додавати продукти до кошика.
   - Видаляти продукти з кошика.
   - Робити замовлення з товарів у кошику.
   - Перевіряти статус замовлення.

2. Після реалізації системи електронної комерції:
   - Напишіть тестові випадки JUnit для тестування всіх функцій. 
   - Замокайте поведінку методів

***
### Хід розробки:

Першим було створено клас `Product`, котрий вміщує в собі інформацію про продукт, а саме його ідентифікатор, назву та ціну

Повний вигляд класу `Prosuct` :
```java
package org.example;

public class Product {
  private final int id;
  private final String name;
  private final double price;

  public Product(int id, String name, double price) {
    this.id = id;
    this.name = name;
    this.price = price;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public double getPrice() {
    return price;
  }
}
```
Наступним кроком було створення класів `Cart` та `Order`. `Cart` зберігає в собі інформацію про список продуктів, `Order` - інформацію про замовлення, а саме ідентифікатор замовлення, продукти, що входять в замовлення та статус.

Повний вигляд класу `Cart`
```java
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
```

Повний вигляд класу `Order`:
```java
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
```

Останнім для розробки програми був крок створення головного методу `Main`, в котрому просто виводиться в консоль повідомлення, що програма працює. Це було зроблено, бо програма розроблялась в першу чергу для використання в ній JUnit та Mockito, тому початкових даних в ній не було.

Остаточно завершаючим етапом було тестування за допомогою JUnit та Mockito - для цього було створено клас `CartTest`.

Повний вигляд класу `CartTest`:
```java
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
```
Тестування показало, що все працює належним чином.

pom.xml буде знаходитись в основній теці лабораторної роботи, разом з readme.md