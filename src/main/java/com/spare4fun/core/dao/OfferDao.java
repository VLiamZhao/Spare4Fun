package com.spare4fun.core.dao;

import com.spare4fun.core.entity.Offer;

import java.util.List;

public interface OfferDao {
    /**
     * save a new offer to offer table
     * TODO check no duplicate (user, item) pair
     * @param Offer
     * @return Offer that is saved to table
     */
    Offer saveOffer(Offer Offer);

    /**
     * Get all offers for user with userId as buyer
     * @author Yuhe Gu
     * @param userId
     * @return list of offers under this user as buyer
     */
    List<Offer> getAllOffersBuyer(int userId);

    /**
     * Get all offers for user with userId as seller
     * @author Yuhe Gu
     * @param userId
     * @param itemId
     * @return list of offers under this user as seller
     */
    List<Offer> getAllOffersSeller(int userId, int itemId);

    /**
     * Get an offer by offerId
     * @param offerId
     * @return null iff offer doesn't exist
     */
    Offer getOfferById(int offerId);


    /**
     * delete the offer with offerId from offer table
     * @author Yuhe Gu
     * @param offerId
     */
    void deleteOfferById(int offerId);
}
