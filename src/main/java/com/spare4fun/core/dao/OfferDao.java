package com.spare4fun.core.dao;

import com.spare4fun.core.entity.Offer;

import java.util.List;

public interface OfferDao {
    Offer saveOffer(Offer Offer);

    //******
    //Yuhe
    List<Offer> getAllOffersByUsername(String username);

    /**
     *
     * @param offerId
     * @return null iff offer doesn't exist ---- design 1
     *
     */
    Offer getOfferById(int offerId);

    void deleteOffer(int offerId);
}
