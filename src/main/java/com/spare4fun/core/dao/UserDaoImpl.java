package com.spare4fun.core.dao;

import com.spare4fun.core.entity.User;
import com.spare4fun.core.exception.DuplicateUserException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Optional;

@Repository
public class UserDaoImpl implements UserDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Optional<User> selectUserByUsername(String username) {
        User user = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<User> criteriaQuery = builder.createQuery(User.class);
            Root<User> root = criteriaQuery.from(User.class);
            criteriaQuery
                    .select(root)
                    .where(builder.equal(root.get("email"), username));
            user = session
                    .createQuery(criteriaQuery)
                    .getSingleResult();
            session.getTransaction().commit();
        } catch (NoResultException e) {
            return Optional.empty();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return Optional.ofNullable(user);
    };

    @Override
    public void addUser(User user) throws DuplicateUserException {
        Optional<User> dup = selectUserByUsername(user.getUsername());
        if (dup.isPresent()) {
            throw new DuplicateUserException("User " + user.getUsername() + " already exist");
        }

        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void deleteUserByUsername(String username) throws UsernameNotFoundException {
         selectUserByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User " + username + " does not exist")
                );

        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaDelete<User> criteriaQuery = builder.createCriteriaDelete(User.class);
            Root<User> root = criteriaQuery.from(User.class);
            criteriaQuery.where(builder.equal(root.get("email"), username));
            session.createQuery(criteriaQuery).executeUpdate();
            session.getTransaction().commit();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}