package com.spare4fun.core.dao;

import com.spare4fun.core.entity.Offer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;

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
}
