package com.spare4fun.core.dao;

import com.spare4fun.core.dto.OfferDto;
import com.spare4fun.core.entity.Offer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class OfferDaoImpl implements OfferDao {
    @Autowired
    private SessionFactory sessionFactory;

    public Offer saveOffer(Offer offer) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.save(offer);
            session.getTransaction().commit();
            return offer;
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return null;
    }

    //******
    //Yuhe
    public List<Offer> getAllOffers() {
        List<Offer> offers = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            offers = session.createCriteria(Offer.class).list();  ////how to transit the data
        } catch (Exception e) {
            e.printStackTrace();
        }

        return offers;
    }

    public Offer getOfferById(int offerId) {
        Offer offer = null;
        try (Session session = sessionFactory.openSession()) {
            offer = session.get(Offer.class, offerId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return offer;
    }

    public void deleteOffer(int offerId) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            Offer offer = session.get(Offer.class, offerId);
            //List<Offer> offers =

            session.beginTransaction();
            session.delete(offer);
            session.getTransaction().commit();
        } catch (Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
