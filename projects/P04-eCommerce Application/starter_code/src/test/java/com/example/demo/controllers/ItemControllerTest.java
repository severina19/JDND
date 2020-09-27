package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.example.demo.TestUtils.createItem;
import static com.example.demo.TestUtils.createItems;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ItemControllerTest {

    @InjectMocks
    private  ItemController itemController;
    @Mock
    private ItemRepository itemRepository;

    @Before
    public void setUp(){
        when(itemRepository.findById(1L)).thenReturn(Optional.of(createItem(1)));
        when(itemRepository.findAll()).thenReturn(createItems());
        when(itemRepository.findByName("item")).thenReturn(Arrays.asList(createItem(1), createItem(2)));
    }

    @Test
    public void testgetItems() throws Exception{
        final ResponseEntity<List<Item>> responseEntity = itemController.getItems();

        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCodeValue());
    }
    @Test
    public void testgetItemById() throws Exception{
        final ResponseEntity<Item> responseEntity = itemController.getItemById(1L);

        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCodeValue());
    }

    @Test
    public void testgetItemByName() throws Exception{
        final ResponseEntity<List<Item>> responseEntity = itemController.getItemsByName("item");

        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCodeValue());
    }
}
