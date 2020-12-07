package com.spare4fun.core.test.service;

import com.spare4fun.core.CoreApplication;
import com.spare4fun.core.dao.ItemDao;
import com.spare4fun.core.entity.Item;
import com.spare4fun.core.entity.User;
import com.spare4fun.core.service.ItemService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CoreApplication.class)
public class ItemServiceTest {
    @Autowired
    ItemService itemService;

    @Autowired
    private ItemDao itemDao;

    private Item dummyItem;

    // want to get from test
    private User seller;
    private Item title;
    private Item location;
    private Item listingPrice;
    private Item quantity;

    // set up from what I want to get tested
    @Before
    public setup() {
        seller = User
                .builder()
                .
                .build();
    }

    @After



    @Test
    public void saveItemTest() {

    }

}
