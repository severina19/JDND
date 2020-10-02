package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderControllerTest {

    private  OrderController orderController;

    private  UserRepository userRepository = mock(UserRepository.class);
    private OrderRepository orderRepository = mock(OrderRepository.class);

    @Before
    public void setUp(){
        orderController = new OrderController();
        TestUtils.injectObjects(orderController, "userRepository", userRepository);
        TestUtils.injectObjects(orderController, "orderRepository", orderRepository);

    }
    @Test
    public void testSubmitOrder(){
        long id = 1L;
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("testPassword");
        user.setId(id);

        Item item = new Item();
        item.setPrice(new BigDecimal(0.1));
        item.setId(3L);
        item.setDescription("This is a strange item");
        item.setName("Strange stuff");

        Cart cart = new Cart();
        cart.setId(2L);
        cart.addItem(item);
        cart.addItem(item);
        cart.setUser(user);
        user.setCart(cart);
        when(userRepository.findByUsername("testUser")).thenReturn(user);
        ResponseEntity<UserOrder> response = orderController.submit("testUser");
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());

        UserOrder responseUserOrder = response.getBody();
        assertNotNull(responseUserOrder);
        assertEquals("testUser", responseUserOrder.getUser().getUsername());
        assertEquals(item, responseUserOrder.getItems().get(0));
    }

    @Test
    public void test_getOrdersForUser(){
        long id = 1L;
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("testPassword");
        user.setId(id);

        Item item = new Item();
        item.setPrice(new BigDecimal(0.1));
        item.setId(3L);
        item.setDescription("This is a strange item");
        item.setName("Strange stuff");
        List<Item> items = new ArrayList<>();
        items.add(item);

        UserOrder userOrder = new UserOrder();
        userOrder.setUser(user);
        userOrder.setId(1L);
        userOrder.setItems(items);
        userOrder.setTotal(new BigDecimal(1.2));
        List<UserOrder> userOrders = new ArrayList<>();
        userOrders.add(userOrder);

        when(userRepository.findByUsername("testUser")).thenReturn(user);
        when(orderRepository.findByUser(user)).thenReturn(userOrders);
        ResponseEntity<List<UserOrder>> response = orderController.getOrdersForUser("testUser");
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());

        List<UserOrder> responseUserOrderList = response.getBody();
        assertNotNull(responseUserOrderList);
        UserOrder responseUserOrderFirst = responseUserOrderList.get(0);
        assertEquals("testUser", responseUserOrderFirst.getUser().getUsername());
        assertEquals(item, responseUserOrderFirst.getItems().get(0));
    }




}
