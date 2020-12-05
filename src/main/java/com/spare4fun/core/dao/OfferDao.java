package com.spare4fun.core.dao;

import com.spare4fun.core.entity.Offer;

import java.util.List;

public interface OfferDao {
    Offer saveOffer(Offer Offer);

    /**
     * Get all offers by username
     * @author Yuhe Gu
     * @param userId
     * @return
     */
    List<Offer> getAllOffersBuyer(int userId);

    List<Offer> getAllOffersSeller(int userId);

    /**
     * Get an offer by offerId
     * @param offerId
     * @return null iff offer doesn't exist
     */
    Offer getOfferById(int offerId);


    void deleteOfferById(int offerId);
}
