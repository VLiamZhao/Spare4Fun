package com.spare4fun.core.dao;

import com.spare4fun.core.entity.Offer;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class OfferDaoImpl implements OfferDao {
    @Autowired
    SessionFactory sessionFactory;

    @Override
    public List<Offer> getOffersByName(String name) {
        String hql = "From User as u left join fetch u.sellerOffer as slo where cu.name = :cuId";
//        String hql = "From Customer as cu left join fetch cu.images as img WHERE cu.id = :cuId";
        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            Query<Customer> query = session.createQuery(hql);
            query.setParameter("cuId", id);
            return query.uniqueResult();
        }
        catch (Exception e){
            logger.error(e.getMessage());
        }

        return null;
    }

    @Override
    public List<Offer> getOffers() {
        return null;
    }

    @Override
    public Offer getOfferById(long id) {
        return null;
    }

    @Override
    public Offer save(Offer Offer) {
        return null;
    }

    @Override
    public boolean deleteOfferById(long id) {
        return false;
    }

    @Override
    public Offer updateOffer(Offer offer) {
        return null;
    }
}
