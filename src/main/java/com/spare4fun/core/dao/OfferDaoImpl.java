package com.spare4fun.core.dao;

import com.spare4fun.core.dto.OfferDto;
import com.spare4fun.core.entity.Offer;
import com.spare4fun.core.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class OfferDaoImpl implements OfferDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
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

    @Override
    public List<Offer> getAllOffersBuyer(int userId) {
        List<Offer> offers = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Offer> criteriaQuery = builder.createQuery(Offer.class);
            Root<Offer> root = criteriaQuery.from(Offer.class);
            criteriaQuery
                    .select(root)
                    .where(builder.equal(root.get("buyer"), userId));
            offers = session
                    .createQuery(criteriaQuery)
                    .getResultList();
            session.getTransaction().commit();
        } catch (NoResultException e) {
            return Collections.emptyList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return offers;
    }

    @Override
    public List<Offer> getAllOffersSeller(int userId, int itemId) {
        List<Offer> offers = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Offer> criteriaQuery = builder.createQuery(Offer.class);
            Root<Offer> root = criteriaQuery.from(Offer.class);
            criteriaQuery
                    .select(root)
                    .where(builder.and(
                            builder.equal(root.get("seller"), userId),
                            builder.equal(root.get("item"), itemId)
                    ));
            offers = session
                    .createQuery(criteriaQuery)
                    .getResultList();
            session.getTransaction().commit();
        } catch (NoResultException e) {
            return Collections.emptyList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return offers;
    }

    @Override
    public Offer getOfferById(int offerId) {
        Offer offer = null;
        try (Session session = sessionFactory.openSession()) {
            offer = session.get(Offer.class, offerId);
        }catch (NoResultException e) {
            return null;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return offer;
    }

    @Override
    public void deleteOfferById(int offerId) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            Offer offer = session.get(Offer.class, offerId);
            session.beginTransaction();
            session.delete(offer);
            session.getTransaction().commit();
        }
        catch (Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
