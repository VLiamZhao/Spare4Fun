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
        userDao.saveUser(seller);

        buyer = User
                .builder()
                .email("dummy1")
                .password("000")
                .role(Role.USER)
                .enabled(true)
                .build();
        userDao.saveUser(buyer);

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
        offerService.deleteOfferById(dummyOffer.getId());
        offerService.deleteOfferById(offer.getId());
        itemService.deleteItem(item.getId());
        userDao.deleteUserById(seller.getId());
        userDao.deleteUserById(buyer.getId());
        locationDao.deleteLocationById(location.getId());
    }

    @Test
    public void saveOfferTest(){
        Assert.assertNotNull(dummyOffer);
        Assert.assertNotEquals(0, dummyOffer.getId());
    }

    @Test
    public void getAllOffersBuyerTest(){
        List<Offer> offers = offerService.getAllOffersBuyer("dummy1");
        Assert.assertNotNull(offers);
        Assert.assertFalse(offers.isEmpty());
    }

    @Test
    public void getAllOffersSellerTest(){
        List<Offer> offers = offerService.getAllOffersSeller("dummy0");
        Assert.assertNotNull(offers);
        Assert.assertFalse(offers.isEmpty());
    }
}
