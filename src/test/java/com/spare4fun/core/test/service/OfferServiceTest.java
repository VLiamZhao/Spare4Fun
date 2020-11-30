package com.spare4fun.core.test.service;

import com.spare4fun.core.CoreApplication;
import com.spare4fun.core.entity.Item;
import com.spare4fun.core.entity.Offer;
import com.spare4fun.core.entity.Role;
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

import java.util.ArrayList;
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
    private Item dummyItem;

    // test get
    private Item item;
    private User seller;
    private User buyer;
    private Offer offer;
    private Offer savedOffer;

    @Before
    public void setUp() throws DuplicateUserException {
//        seller = User
//                .builder()
//                .email("dummy0")
//                .role(Role.ADMIN)
//                .enabled(true)
//                .build();
//
//        buyer = User
//                .builder()
//                .email("dummy1")
//                .role(Role.USER)
//                .enabled(true)
//                .build();

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
        savedOffer = offerService.saveOffer(offer);

        dummyItem = Item.builder().build();
        dummyItem.setSeller(seller);

        dummyOffer = Offer.builder().build();
        dummyOffer.setItem(item);
    }

    @After
    public void clean() {
        itemService.deleteItem(item.getId());
        offerService.deleteOffer(offer.getId());
        offerService.deleteOffer(savedOffer.getId());
    }

    @Test
    public void saveOfferTest(){
        Offer o1 = offerService.saveOffer(offer);
        Assert.assertNotNull(o1);
        Assert.assertNotEquals(0, o1.getId());
    }

    @Test
    public void getAllOffersTest(){
        //cannot get offers with non-existed user
        List<Offer> offers = offerService.getAllOffers("dummy1");
        // at this point no offer is made by this user
        Assert.assertNotNull(offers);
        Assert.assertTrue(offers.isEmpty());
    }


}
