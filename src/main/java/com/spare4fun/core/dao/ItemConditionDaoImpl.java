package com.spare4fun.core.dao;

import com.spare4fun.core.entity.ItemCondition;
import com.spare4fun.core.exception.DuplicateItemConditionException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class ItemConditionDaoImpl implements ItemConditionDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public ItemCondition saveItemCondition(ItemCondition itemCondition) {
        ItemCondition dup = getItemConditionByName(itemCondition.getLabel());
        if (dup != null) {
            throw new DuplicateItemConditionException(
                    String.format("ItemCondition %s already exists", itemCondition.getLabel()));
        }

        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.save(itemCondition);
            session.getTransaction().commit();
            return itemCondition;
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
    public void deleteItemConditionById(int itemConditionId) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            ItemCondition itemCondition = session.get(ItemCondition.class, itemConditionId);
            session.delete(itemCondition);
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
    public void deleteItemConditionByName(String itemConditionName) {
        ItemCondition itemCondition = getItemConditionByName(itemConditionName);
        if (itemCondition == null) return;

        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.delete(itemCondition);
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
    public ItemCondition getItemConditionById(int itemConditionId) {
        ItemCondition itemCondition = null;
        try (Session session = sessionFactory.openSession()) {
            itemCondition = session.get(ItemCondition.class, itemConditionId);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return itemCondition;
    }

    @Override
    public ItemCondition getItemConditionByName(String itemConditionName) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<ItemCondition> criteriaQuery = builder.createQuery(ItemCondition.class);
            Root<ItemCondition> root = criteriaQuery.from(ItemCondition.class);
            criteriaQuery
                    .select(root)
                    .where(builder.equal(root.get("label"), itemConditionName));
            return session
                    .createQuery(criteriaQuery)
                    .getSingleResult();
        } catch(NoResultException e) {
            // do nothing here, allowed situation
        } catch (Exception e) {
            // for debug purpose
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<ItemCondition> getAllItemConditions() {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<ItemCondition> criteriaQuery = builder.createQuery(ItemCondition.class);
            Root<ItemCondition> root = criteriaQuery.from(ItemCondition.class);
            criteriaQuery.select(root);
            return session.createQuery(criteriaQuery).getResultList();
        } catch(Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public ItemCondition updateItemCondition(ItemCondition itemCondition) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.saveOrUpdate(itemCondition);
            session.getTransaction().commit();
            return itemCondition;
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
