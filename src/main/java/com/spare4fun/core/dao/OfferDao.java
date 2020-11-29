package com.spare4fun.core.dao;

import com.spare4fun.core.dto.OfferDto;
import com.spare4fun.core.entity.Offer;

import java.util.List;

public interface OfferDao {
    Offer saveOffer(Offer Offer);

    //******
    //Yuhe
    List<Offer> getAllOffers();

    /**
     *
     * @param offerId
     * @return null iff offer doesn't exist ---- design 1
     * @throws java.util.NoSuchElementException iff no offer exist ---- design 2
     * @return Optional.empty() iff offer doesn't exist -------- design 3
     */
    Offer getOfferById(int offerId);

    void deleteOffer(int offerId);
}
