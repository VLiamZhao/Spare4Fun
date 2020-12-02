package com.spare4fun.core.test.dao;

import com.spare4fun.core.CoreApplication;
import com.spare4fun.core.dao.ItemDao;
import com.spare4fun.core.dao.LocationDao;
import com.spare4fun.core.dao.UserDao;
import com.spare4fun.core.entity.*;
import com.spare4fun.core.exception.DuplicateUserException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = CoreApplication.class)
public class ItemDaoTest {

    @Autowired
    ItemDao itemDao;

    @Autowired
    UserDao userDao;

    @Autowired
    LocationDao locationDao;

    private Item dummyItem;
    private Item item;
    private Location location;
    private User seller;

    @Before
    public void setUp() throws DuplicateUserException {
        seller = User
                .builder()
                .email("dummy0")
                .password("000")
                .build();
        userDao.addUser(seller);

        location = Location
                .builder()
                .build();
        locationDao.saveLocation(location);

        item = Item
                .builder()
                .seller(seller)
                .location(location)
                .build();
        itemDao.saveItem(item);

        dummyItem = Item
                .builder()
                .seller(seller)
                .location(location)
                .build();
        itemDao.saveItem(dummyItem);
    }

    @After
    public void clean() {
        if (itemDao.getItemById(dummyItem.getId()) != null) {
            itemDao.deleteItem(dummyItem.getId());
        }
        if (itemDao.getItemById(item.getId()) != null) {
            itemDao.deleteItem(item.getId());
        }

        userDao.deleteUserByUsername(seller.getUsername());
        locationDao.deleteLocation(location.getId());
    }

    @Test
    public void saveItemTest(){
        Assert.assertNotNull(dummyItem);
        Assert.assertNotEquals(0, dummyItem.getId());
    }

    @Test
    public void getItemByIdTest(){
        //cannot get items with non-existed itemId
        Assert.assertNotNull(itemDao.getItemById(item.getId()));
    }

    @Test
    public void deleteItemTest() {
        //cannot delete items with non-existed itemId
        Assert.assertNotNull(item);
        Assert.assertNotEquals(0, item.getId());
        //delete
        itemDao.deleteItem(item.getId());
        //get id
        Assert.assertNull(itemDao.getItemById(item.getId()));
    }
}
