package com.spare4fun.core.test.service;

import com.spare4fun.core.CoreApplication;
import com.spare4fun.core.dao.LocationDao;
import com.spare4fun.core.dao.UserDao;
import com.spare4fun.core.entity.*;
import com.spare4fun.core.exception.DuplicateUserException;
import com.spare4fun.core.service.ItemService;
import com.spare4fun.core.service.OfferService;
import com.spare4fun.core.service.UserService;
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

    @Autowired
    private UserDao userDao;

    @Autowired
    private LocationDao locationDao;

    // test add dummy offer
    private Offer dummyOffer;

    // test get
    private Location location;
    private Item item;
    private User seller;
    private User buyer;
    private Offer offer;

    @Before
    public void setUp() throws DuplicateUserException {
        seller = User
                .builder()
                .email("dummy0")
                .password("000")
                .role(Role.ADMIN)
                .enabled(true)
                .build();
        userDao.addUser(seller);

        buyer = User
                .builder()
                .email("dummy1")
                .password("000")
                .role(Role.USER)
                .enabled(true)
                .build();
        userDao.addUser(buyer);

        location = Location
                .builder()
                .build();
        locationDao.saveLocation(location);

        item = Item
                .builder()
                .seller(seller)
                .location(location)
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
                .buyer(buyer)
                .seller(seller)
                .build();
        offerService.saveOffer(dummyOffer);
    }

    @After
    public void clean() {
        offerService.deleteOffer(dummyOffer.getId());
        offerService.deleteOffer(offer.getId());
        itemService.deleteItemById(item.getId());
        userDao.deleteUserByUsername(seller.getUsername());
        userDao.deleteUserByUsername(buyer.getUsername());
        locationDao.deleteLocationById(location.getId());
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
