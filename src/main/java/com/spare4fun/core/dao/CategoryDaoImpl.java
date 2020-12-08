package com.spare4fun.core.dao;

import com.spare4fun.core.entity.Category;
import com.spare4fun.core.exception.DuplicateCategoryException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class CategoryDaoImpl implements CategoryDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Category saveCategory(Category category) {
        Category dup = getCategoryByName(category.getCategory());
        if (dup != null) {
            throw new DuplicateCategoryException(
                    String.format("Category %s already exists", category.getCategory()));
        }

        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.save(category);
            session.getTransaction().commit();
            return category;
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
    public void deleteCategoryById(int categoryId) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            Category category = session.get(Category.class, categoryId);
            session.delete(category);
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
    public void deleteCategoryByName(String categoryName) {
        Category category = getCategoryByName(categoryName);
        if (category == null) return;

        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.delete(category);
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
    public Category getCategoryById(int categoryId) {
        Category category = null;
        try (Session session = sessionFactory.openSession()) {
            category = session.get(Category.class, categoryId);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return category;
    }

    @Override
    public Category getCategoryByName(String categoryName) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Category> criteriaQuery = builder.createQuery(Category.class);
            Root<Category> root = criteriaQuery.from(Category.class);
            criteriaQuery
                    .select(root)
                    .where(builder.equal(root.get("category"), categoryName));
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
    public List<Category> getAllCategories() {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Category> criteriaQuery = builder.createQuery(Category.class);
            Root<Category> root = criteriaQuery.from(Category.class);
            criteriaQuery.select(root);
            return session.createQuery(criteriaQuery).getResultList();
        } catch(Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public Category updateCategory(Category category) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.saveOrUpdate(category);
            session.getTransaction().commit();
            return category;
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
