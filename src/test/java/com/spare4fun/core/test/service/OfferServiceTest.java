package com.spare4fun.core.test.service;

import com.spare4fun.core.CoreApplication;
import com.spare4fun.core.entity.Item;
import com.spare4fun.core.entity.Offer;
import com.spare4fun.core.entity.User;
import com.spare4fun.core.exception.DuplicateUserException;
import com.spare4fun.core.service.ItemService;
import com.spare4fun.core.service.OfferService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CoreApplication.class)
public class OfferServiceTest {
    @Autowired
    OfferService offerService;

    @Autowired
    ItemService itemService;

    // test add dummy offer
    private Offer dummyOffer;

    // test get
    private Item item;
    private User seller;
    private User buyer;
    private Offer offer;

    @Before
    public void setUp() throws DuplicateUserException {
        item = Item
                .builder()
                .seller(seller)
                .build();
        itemService.saveItem(item);

        offer = Offer
                .builder()
                .item(item)
                .buyer(buyer)
                .seller(seller)
                .message("123")
                .build();
        offerService.saveOffer(offer);


        dummyOffer = Offer
                .builder()
                .item(item)
                .build();
        offerService.saveOffer(dummyOffer);
    }

    @After
    public void clean() {
        offerService.deleteOffer(dummyOffer.getId());
        offerService.deleteOffer(offer.getId());
        itemService.deleteItem(item.getId());
    }

    @Test
    public void saveOfferTest(){
        Assert.assertNotNull(dummyOffer);
        Assert.assertNotEquals(0, dummyOffer.getId());
    }

    @Test
    public void getAllOffersTest(){
        List<Offer> offers = offerService.getAllOffers("dummy1");
        Assert.assertNotNull(offers);
        Assert.assertTrue(offers.isEmpty());
    }


}
