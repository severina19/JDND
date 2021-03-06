package com.example.demo;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class TestUtils {

    public static void injectObjects(Object target,
                                     String fieldName,
                                     Object toInject){
        boolean wasPrivate = false;
        try {
            Field f = target.getClass().getDeclaredField(fieldName);
            if (! f.isAccessible()){
                f.setAccessible(true);
                wasPrivate = true;
            }
            f.set(target, toInject);
            if (wasPrivate){
                f.setAccessible(false);
            }
        }
        catch (NoSuchFieldException | IllegalAccessException e){
            e.printStackTrace();
        }
    }

    public static Item createItem(long id){
        Item item = new Item();
        item.setId(id);
        item.setPrice(new BigDecimal(42));
        item.setName("bigItem"+id);
        item.setDescription("This is a big item");
        return item;
    }

    public static List<Item> createItems() {
        List<Item> items = new ArrayList<>();
        items.add(createItem(1));
        items.add(createItem(2));
        return items;
    }

    public static Cart createCart(User user) {
        Cart cart = new Cart();
        cart.setUser(user);
        cart.setItems(createItems());
        cart.setId(1L);
        return cart;
    }

    public static User createUser(){
        long id = 1L;
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("testPassword");
        user.setId(id);
        Cart cart = new Cart();
        user.setCart(cart);
        return user;
    }

}
