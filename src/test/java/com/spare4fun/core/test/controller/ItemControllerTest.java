package com.spare4fun.core.test.controller;

import com.spare4fun.core.dao.UserDao;
import com.spare4fun.core.entity.Item;
import com.spare4fun.core.entity.Location;
import com.spare4fun.core.entity.User;
import com.spare4fun.core.exception.DuplicateUserException;
import com.spare4fun.core.service.ItemService;
import com.spare4fun.core.service.LocationService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
public class ItemControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDao userDao;

    @Autowired
    private ItemService itemService;

    @Autowired
    private LocationService locationService;

    private User seller;
    private Location location;
    private Map<Integer, Item> items;

    @BeforeEach
    private void setup() {
        seller = User
                .builder()
                .email("dummy@gmail.com")
                .password(passwordEncoder.encode("pass"))
                .build();
        try {
            userDao.addUser(seller);
        } catch (DuplicateUserException e) {
            e.printStackTrace();
        }

        location = Location.builder().build();
        locationService.saveLocation(location);

        items = new HashMap<>();
        setDummyItems()
                .stream()
                .forEach( item -> {
                    items.put(item.getId(), item);
                });
    }

    @AfterEach
    private void clean() {
        items.keySet().forEach(itemId -> {
            itemService.deleteItemById(itemId);
        });
        locationService.deleteLocationById(location.getId());
        userDao.deleteUserById(seller.getId());
    }

    private List<Item> setDummyItems() {
        List<Item> res = new ArrayList<>();

        Item item1 = Item
                .builder()
                .title("CLRS")
                .seller(seller)
                .location(location)
                .build();
        res.add(item1);
        itemService.saveItem(item1);

        Item item2 = Item
                .builder()
                .title("Computer Architecture")
                .seller(seller)
                .location(location)
                .build();
        res.add(item2);
        itemService.saveItem(item2);

        return res;
    }

    @Test
    public void testGetAllItems() throws Exception {
    }
}
