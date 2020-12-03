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

    public List<Offer> getAllOffersSeller(String username) {
        User user = null;
        try {
            user = userDao.selectUserByUsername(username).get();
        } catch (NoSuchElementException e) {
            return Collections.emptyList();
        }
        return offerDao.getAllOffersBuyer(user.getId());
    }

    public Offer getOfferById(int offerId) {
        return offerDao.getOfferById(offerId);
    }

    public void deleteOfferById(int offerId) {
        offerDao.deleteOfferById(offerId);
    }
}
