package com.spare4fun.core.service;

import com.spare4fun.core.dao.OfferDao;
import com.spare4fun.core.entity.Offer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OfferService {
    @Autowired
    OfferDao offerDao;

    public Offer saveOffer(Offer offer){
        return offerDao.saveOffer(offer);
    }


    //*****
    //Yuhe
    public List<Offer> getAllOffers() {
        return offerDao.getAllOffersByUsername(String username);
    }

    public void deleteOffer(int offerId) {
        offerDao.deleteOffer(offerId);
    }
}
