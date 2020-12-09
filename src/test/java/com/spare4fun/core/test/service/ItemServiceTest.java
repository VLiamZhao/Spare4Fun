package com.spare4fun.core.test.service;

import com.spare4fun.core.dao.LocationDao;
import com.spare4fun.core.dao.UserDao;
import com.spare4fun.core.entity.Item;
import com.spare4fun.core.entity.Location;
import com.spare4fun.core.entity.Role;
import com.spare4fun.core.entity.User;
import com.spare4fun.core.exception.InvalidPriceException;
import com.spare4fun.core.exception.InvalidQuantityException;
import com.spare4fun.core.service.ItemService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class ItemServiceTest {
    @Autowired
    private ItemService itemService;

    @Autowired
    private UserDao userDao;

    @Autowired
    private LocationDao locationDao;

    private Item dummyItem;

    private User seller;
    private Location location;

    // set up from what I want to get tested
    @BeforeEach
    public void setup() {
        seller = User
                .builder()
                .email("dummy02")
                .password("123456")
                .role(Role.ADMIN)
                .enabled(true)
                .build();
        userDao.saveUser(seller);

        location = Location
                .builder()
                .build();
        locationDao.saveLocation(location);

        dummyItem = Item
                .builder()
                .seller(seller)
                .location(location)
                .quantity(3)
                .listingPrice(5)
                .build();
    }

    @AfterEach
    public void clean() {
        userDao.deleteUserById(seller.getId());
        locationDao.deleteLocationById(location.getId());
    }

    @Test
    public void saveItemTest() {
        assertThrows(
                InvalidPriceException.class,
                () -> {
                    itemService.saveItem(
                            Item
                                    .builder()
                                    .listingPrice(-1)
                                    .quantity(4)
                                    .build()
                    );
                }
        );

        assertThrows(
                InvalidQuantityException.class,
                () -> {
                    itemService.saveItem(
                            Item
                            .builder()
                            .listingPrice(1)
                            .quantity(-2)
                            .build()
                    );
                }
        );

        Item savedItem = itemService.saveItem(dummyItem);
        assertEquals(savedItem.getId(), dummyItem.getId());
        itemService.deleteItemById(dummyItem.getId());
    }
}
