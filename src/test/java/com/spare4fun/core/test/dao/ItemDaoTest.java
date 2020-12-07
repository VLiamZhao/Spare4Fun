package com.spare4fun.core.test.dao;

import com.spare4fun.core.dao.ItemDao;
import com.spare4fun.core.dao.LocationDao;
import com.spare4fun.core.dao.UserDao;
import com.spare4fun.core.entity.Item;
import com.spare4fun.core.entity.Location;
import com.spare4fun.core.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ItemDaoTest {
    @Autowired
    private ItemDao itemDao;

    @Autowired
    private LocationDao locationDao;

    @Autowired
    private UserDao userDao;

    private Location location;
    private User seller;
    private List<Item> items;

    @BeforeEach
    public void setup() {
        location = Location
                .builder()
                .line1("1")
                .city("A")
                .state("WA")
                .build();
        locationDao.saveLocation(location);

        seller = User
                .builder()
                .email("dummy1")
                .password("pass")
                .build();
        userDao.saveUser(seller);

        items = dummyItems();
        items.forEach(item -> {
            itemDao.saveItem(item);
        });
    }

    @AfterEach
    public void clean() {
        items.forEach(item -> {
            itemDao.deleteItemById(item.getId());
        });
        locationDao.deleteLocationById(location.getId());
        userDao.deleteUserById(seller.getId());
    }

    public List<Item> dummyItems() {
        List<Item> res = new ArrayList<>();

        res.add(
                Item
                        .builder()
                        .seller(seller)
                        .location(location)
                        .title("CLRS")
                        .build()
        );

        res.add(
                Item
                        .builder()
                        .seller(seller)
                        .location(location)
                        .title("Computer Architecture")
                        .build()
        );

        return res;
    }

    @Test
    public void testSaveItem() {
        items.forEach(item -> {
            assertThat(itemDao.getItemById(item.getId())).isNotNull();
        });
    }

    @Test
    public void testDeleteItem() {
        Item item = Item
                .builder()
                .seller(seller)
                .location(location)
                .title("Computer Architecture")
                .build();
        itemDao.saveItem(item);
        assertThat(itemDao.getItemById(item.getId())).isNotNull();
        itemDao.deleteItemById(item.getId());
        assertThat(itemDao.getItemById(item.getId())).isNull();
    }

    @Test
    public void testGetItemById() {
        Item item = itemDao.getItemById(items.get(0).getId());
        assertThat(item.getId()).isEqualTo(items.get(0).getId());
        assertThat(item.getTitle()).isEqualTo(items.get(0).getTitle());
        assertThat(item.getSeller().getId()).isEqualTo(items.get(0).getSeller().getId());
        assertThat(item.getLocation().getId()).isEqualTo(items.get(0).getLocation().getId());
    }

    @Test
    public void testGetAllItems() {
        List<Item> itemsFromTable = itemDao.getAllItems();
        assertThat(itemsFromTable.size()).isEqualTo(items.size());
    }
}
