package com.spare4fun.core.service;

import com.spare4fun.core.dao.OfferDao;
import com.spare4fun.core.dao.UserDao;
import com.spare4fun.core.entity.Item;
import com.spare4fun.core.entity.Offer;
import com.spare4fun.core.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import javax.transaction.Transactional;
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

    @Autowired
    private ItemService itemService;

    /**
     * TODO 1: need to check item.quantity >= offer.quantity
     * TODO 2: need to modify item.quantity -= offer.quantity
     * TODO 3: need to modify item.quantity_on_hold += offer.quantity
     * TODO 4: corner cases - offer.price > 0 & offer.quantity > 0
     * @param offer
     * @return
     */
    @Transactional
    public Offer saveOffer(Offer offer) {
        Item currentItem = offer.getItem();
        if (offer.getPrice() <= 0) {
            throw new RuntimeException("Wrong price!");
        } else if (offer.getQuantity() <= 0) {
            throw new RuntimeException("Wrong quantity!");
        } else if (currentItem == null) {
            throw new RuntimeException("Cannot find the Item!");
        } else if (offer.getBuyer() == null) {
            throw new RuntimeException("Cannot find the Buyer!");
        } else if (offer.getSeller() == null) {
            throw new RuntimeException("Cannot find the Seller!");
        } else if (offer.getQuantity() > currentItem.getQuantity()) {
            throw new RuntimeException("Item quantity is not sufficient!");
        } else if (offer.getBuyer().getEmail().equals(offer.getSeller().getEmail())) {
            throw new RuntimeException("Buyer and seller cannot be the same!");
        }
        int currentItemQuantity = offer.getItem().getQuantity();
        int currentItemQuantityOnHold = offer.getItem().getQuantityOnHold();
        currentItem.setQuantity(currentItemQuantity - offer.getQuantity());
        currentItem.setQuantityOnHold(currentItemQuantityOnHold + offer.getQuantity());
        itemService.updateItem(offer.getItem());
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
