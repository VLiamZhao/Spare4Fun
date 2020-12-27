package com.spare4fun.core.dao;

import com.spare4fun.core.entity.Offer;
import com.spare4fun.core.entity.PaymentOrder;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
public class PaymentOrderDaoImpl implements PaymentOrderDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public PaymentOrder savePaymentOrder(PaymentOrder paymentOrder) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.save(paymentOrder);
            session.getTransaction().commit();
            return paymentOrder;
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
    public PaymentOrder getPaymentOrderById(int paymentOrderId) {
        PaymentOrder po = null;
        try (Session session = sessionFactory.openSession()) {
            po = session.get(PaymentOrder.class, paymentOrderId);
        }catch (NoResultException e) {
            return null;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return po;
    }

    @Override
    public void deletePaymentOrderById(int paymentOrderId) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            PaymentOrder po = session.get(PaymentOrder.class, paymentOrderId);
            session.beginTransaction();
            session.delete(po);
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

    @Override
    public List<PaymentOrder> getAllPaymentOrdersBuyer(int userId) {
        List<PaymentOrder> pos = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<PaymentOrder> criteriaQuery = builder.createQuery(PaymentOrder.class);
            Root<PaymentOrder> root = criteriaQuery.from(PaymentOrder.class);
            criteriaQuery
                    .select(root)
                    .where(builder.equal(root.get("buyer"), userId));
            pos = session
                    .createQuery(criteriaQuery)
                    .getResultList();
            session.getTransaction().commit();
        } catch (NoResultException e) {
            return Collections.emptyList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pos;
    }

    @Override
    public List<PaymentOrder> getAllPaymentOrdersSeller(int userId, int itemId) {
        List<PaymentOrder> pos = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<PaymentOrder> criteriaQuery = builder.createQuery(PaymentOrder.class);
            Root<PaymentOrder> root = criteriaQuery.from(PaymentOrder.class);
            criteriaQuery
                    .select(root)
                    .where(builder.and(
                            builder.equal(root.get("seller"), userId),
                            builder.equal(root.get("item"), itemId)
                    ));
            pos = session
                    .createQuery(criteriaQuery)
                    .getResultList();
            session.getTransaction().commit();
        } catch (NoResultException e) {
            return Collections.emptyList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pos;
    }
}
