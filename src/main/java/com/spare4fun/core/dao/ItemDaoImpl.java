package com.spare4fun.core.dao;

import com.spare4fun.core.entity.Item;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class ItemDaoImpl implements ItemDao {
    @Autowired
    private SessionFactory sessionFactory;

    public Item createItem(Item item) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.save(item);
            session.getTransaction().commit();
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

    public Item deleteItem(int itemId) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            Item item = session.get(Item.class, itemId);
            session.delete(item);
            session.getTransaction().commit();
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

    public Item getItemById(int itemId) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Item.class, itemId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
