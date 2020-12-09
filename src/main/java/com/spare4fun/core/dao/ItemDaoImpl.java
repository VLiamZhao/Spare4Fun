package com.spare4fun.core.dao;

import com.spare4fun.core.entity.Item;
import com.spare4fun.core.entity.Location;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class ItemDaoImpl implements ItemDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
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

    @Override
    public Item getItemById(int itemId) {
        Item item = null;
        try (Session session = sessionFactory.openSession()) {
            item = session.get(Item.class, itemId);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return item;
    }

    @Override
    public void deleteItemById(int itemId) {
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
    }

    @Override
    public List<Item> getAllItems() {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Item> criteriaQuery = builder.createQuery(Item.class);
            Root<Item> root = criteriaQuery.from(Item.class);
            criteriaQuery.select(root);
            return session.createQuery(criteriaQuery).getResultList();
        } catch(Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public Item updateItem(Item item) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.saveOrUpdate(item);
            session.getTransaction().commit();
            return item;
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}