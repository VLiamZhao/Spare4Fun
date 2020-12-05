package com.spare4fun.core.service;

import com.spare4fun.core.dao.OfferDao;
import com.spare4fun.core.dao.UserDao;
import com.spare4fun.core.entity.Offer;
import com.spare4fun.core.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class OfferService {
    @Autowired
    private OfferDao offerDao;

    @Autowired
    private UserDao userDao;

    /**
     * TODO 1: need to check item.quantity >= offer.quantity
     * TODO 2: need to modify item.quantity -= offer.quantity
     * TODO 3: need to modify item.quantity_on_hold += offer.quantity
     * TODO 4: corner cases - offer.price > 0 & offer.quantity > 0
     * @param offer
     * @return
     */
    public Offer saveOffer(Offer offer){
        return offerDao.saveOffer(offer);
    }


    public List<Offer> getAllOffersBuyer(String username) {
        User user = null;
        try {
            user = userDao.selectUserByUsername(username).get();
        } catch (NoSuchElementException e) {
            return Collections.emptyList();
        }
        return offerDao.getAllOffersBuyer(user.getId());
    }

    // TODO: change this method for corresponding OfferDao::getAllOffersSeller
    /*
    public List<Offer> getAllOffersSeller(String username) {
        User user = null;
        try {
            user = userDao.selectUserByUsername(username).get();
        } catch (NoSuchElementException e) {
            return Collections.emptyList();
        }
        return offerDao.getAllOffersSeller(user.getId());
    }
    */

    public Offer getOfferById(int offerId) {
        return offerDao.getOfferById(offerId);
    }

    public void deleteOfferById(int offerId) {
        offerDao.deleteOfferById(offerId);
    }
}
