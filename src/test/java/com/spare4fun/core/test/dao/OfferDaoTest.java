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
        itemDao.saveItem(item);

        offer = Offer
                .builder()
                .item(item)
                .buyer(buyer)
                .seller(seller)
                .message("123")
                .build();
        offerDao.saveOffer(offer);

        dummyOffer = Offer
                .builder()
                .item(item)
                .buyer(buyer)
                .seller(seller)
                .build();
        offerDao.saveOffer(dummyOffer);
    }

    @After
    public void clean() {
        if (offerDao.getOfferById(dummyOffer.getId()) != null) {
            offerDao.deleteOffer(dummyOffer.getId());
        }
        if (offerDao.getOfferById(offer.getId()) != null) {
            offerDao.deleteOffer(offer.getId());
        }

        itemDao.deleteItem(item.getId());
        userDao.deleteUserByUsername(seller.getUsername());
        userDao.deleteUserByUsername(buyer.getUsername());
        locationDao.deleteLocation(location.getId());
    }

    @Test
    public void saveOfferTest(){
        Assert.assertNotNull(dummyOffer);
        Assert.assertNotEquals(0, dummyOffer.getId());
    }

    @Test
    public void getAllOffersTest(){
        List<Offer> offers = offerDao.getAllOffers(buyer.getId());
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
        offerDao.deleteOffer(offer.getId());
        //get
        Assert.assertNull(offerDao.getOfferById(offer.getId()));
    }

}
