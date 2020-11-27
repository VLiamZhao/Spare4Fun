package com.spare4fun.core.dao;

import com.spare4fun.core.entity.Offer;

import java.util.List;

public interface OfferDao {

    List<Offer> getOffersByName(String name);
    List<Offer> getOffers();
    Offer getOfferById(long id);
    Offer save(Offer Offer);
    boolean deleteOfferById(long id);
    Offer updateOffer(Offer offer);

}
