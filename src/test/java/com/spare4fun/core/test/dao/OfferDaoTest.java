package com.spare4fun.core.test.dao;

import com.spare4fun.core.CoreApplication;
import com.spare4fun.core.dao.ItemDao;
import com.spare4fun.core.dao.OfferDao;
import com.spare4fun.core.dao.UserDao;
import com.spare4fun.core.entity.Item;
import com.spare4fun.core.entity.Offer;
import com.spare4fun.core.entity.Role;
import com.spare4fun.core.entity.User;
import com.spare4fun.core.exception.DuplicateUserException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CoreApplication.class)
public class OfferDaoTest {

    @Autowired
    OfferDao offerDao;

    @Autowired
    ItemDao itemDao;

    @Autowired
    UserDao userDao;

    // test add dummy offer
    private Offer dummyOffer;
//    private Item dummyItem;

    // test get
    private Item item;
    private User seller;
    private User buyer;
    private Offer offer;

    @Before
    public void setUp() throws DuplicateUserException {
        seller = User
                .builder()
                .email("dummy0")
                .role(Role.ADMIN)
                .enabled(true)
                .build();
        userDao.addUser(seller);

        buyer = User
                .builder()
                .email("dummy1")
                .role(Role.USER)
                .enabled(true)
                .build();
        userDao.addUser(buyer);

        item = Item
                .builder()
                .seller(seller)
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
                .build();
        offerDao.saveOffer(dummyOffer);
    }

    @After
    public void clean() {
        if (offerDao.getOfferById(dummyOffer.getId()) != null) {
            offerDao.deleteOffer(dummyOffer.getId());
        }
        offerDao.deleteOffer(offer.getId());
        itemDao.deleteItem(item.getId());
        userDao.deleteUserByUsername(seller.getUsername());
        userDao.deleteUserByUsername(buyer.getUsername());
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
