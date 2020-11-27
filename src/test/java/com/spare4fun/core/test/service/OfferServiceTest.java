package com.spare4fun.core.test.service;

import com.spare4fun.core.CoreApplication;
import com.spare4fun.core.dao.ItemDao;
import com.spare4fun.core.entity.Item;
import com.spare4fun.core.entity.Offer;
import com.spare4fun.core.service.ItemService;
import com.spare4fun.core.service.OfferService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CoreApplication.class)
public class OfferServiceTest {
    @Autowired
    OfferService offerService;

    @Autowired
    ItemService itemService;

    private Offer offer;
    private Item item;

    @Before
    public void setUp(){
        item = new Item();
        offer = new Offer();
        itemService.saveItem(item);
        offer.setItem(item);
    }

    @Test
    public void saveOfferTest(){
        Offer o1 = offerService.saveOffer(offer);
        Assert.assertNotNull(o1);
        Assert.assertNotEquals(0, o1.getId());
    }
}
