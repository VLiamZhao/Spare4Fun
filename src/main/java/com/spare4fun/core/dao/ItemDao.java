package com.spare4fun.core.dao;

import com.spare4fun.core.entity.Item;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ItemDao {
    @Autowired // inversion of controll
    private SessionFactory sessionFactory;

    public List<Item> searchByName(String itemName) {
        List<Item> results = new ArrayList<>();
        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            // ... search logic
            // query
            String hql = "SELECT * FROM item WHERE title = :itemName";
            Query query = session.createQuery(hql);
            results = (List<Item>) query.setParameter("itemName", itemName).list();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return results;
    }
}
