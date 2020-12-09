package com.spare4fun.core.test.dao;

import com.spare4fun.core.CoreApplication;
import com.spare4fun.core.dao.ItemDao;
import com.spare4fun.core.dao.LocationDao;
import com.spare4fun.core.dao.OfferDao;
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

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CoreApplication.class)
public class OfferDaoTest {

    @Autowired
    OfferDao offerDao;

    @Autowired
    ItemDao itemDao;

    @Autowired
    UserDao userDao;

    @Autowired
    LocationDao locationDao;

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
                .quantity(1000)
                .location(location)
                .build();
        itemDao.saveItem(item);

        offer = Offer
                .builder()
                .item(item)
                .buyer(buyer)
                .price(300)
                .quantity(3)
                .seller(seller)
                .message("123")
                .build();
        offerDao.saveOffer(offer);

        dummyOffer = Offer
                .builder()
                .item(item)
                .buyer(buyer)
                .price(300)
                .quantity(3)
                .seller(seller)
                .message("123")
                .build();
        offerDao.saveOffer(dummyOffer);
    }

    @After
    public void clean() {
        if (offerDao.getOfferById(dummyOffer.getId()) != null) {
            offerDao.deleteOfferById(dummyOffer.getId());
        }
        if (offerDao.getOfferById(offer.getId()) != null) {
            offerDao.deleteOfferById(offer.getId());
        }

        itemDao.deleteItemById(item.getId());
        userDao.deleteUserById(seller.getId());
        userDao.deleteUserById(buyer.getId());
        locationDao.deleteLocationById(location.getId());
    }

    @Test
    public void saveOfferTest(){
        Assert.assertNotNull(dummyOffer);
        Assert.assertTrue(dummyOffer.getId() != 0);
    }

    @Test
    public void getAllOffersBuyerTest(){
        List<Offer> offers = offerDao.getAllOffersBuyer(buyer.getId());
        Assert.assertNotNull(offers);
    }

    @Test
    public void getAllOffersSellerTest(){
        List<Offer> offers = offerDao.getAllOffersSeller(seller.getId(), item.getId());
        Assert.assertNotNull(offers);
    }

    @Test
    public void getOfferByIdTest(){
        //cannot get offers with non-existed offerId
        Offer offerTest = offerDao.getOfferById(offer.getId());
        Assert.assertNotNull(offerTest);
        Assert.assertEquals(offerTest.getMessage(), "123");
    }

    @Test
    public void deleteOfferTest() {
        //cannot delete offers with non-existed offerId
        Assert.assertNotNull(offer);
        Assert.assertNotNull(offerDao.getOfferById(offer.getId()));
        //delete
        offerDao.deleteOfferById(offer.getId());
        //get
        Assert.assertNull(offerDao.getOfferById(offer.getId()));
    }

}
