package com.spare4fun.core.dao;

import com.spare4fun.core.entity.Item;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;

@Repository
public class ItemDaoImpl implements ItemDao {
    @Autowired
    private SessionFactory sessionFactory;
    //Please ignore this method. This is only for temporary test
    public Item saveItem(Item item) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.save(item);
            session.getTransaction().commit();
            return item;
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
        Item item = null;
        try (Session session = sessionFactory.openSession()) {
            item = session.get(Item.class, itemId);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return item;
    }

    public void deleteItemById(int itemId) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            Item item = session.get(Item.class, itemId);
            session.delete(item);
            session.getTransaction().commit();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

}