package com.spare4fun.core.dao;

import com.spare4fun.core.entity.Location;
import com.spare4fun.core.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class LocationDaoImpl implements LocationDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Location saveLocation(Location location) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.save(location);
            session.getTransaction().commit();
            return location;
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
    public void deleteLocationById(int locationId) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            Location location = session.get(Location.class, locationId);
            session.delete(location);
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
    public Location getLocationById(int locationId) {
        Location location = null;
        try (Session session = sessionFactory.openSession()) {
            location = session.get(Location.class, locationId);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return location;
    }

    @Override
    public List<Location> getAllLocations() {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Location> criteriaQuery = builder.createQuery(Location.class);
            Root<Location> root = criteriaQuery.from(Location.class);
            criteriaQuery.select(root);
            return session.createQuery(criteriaQuery).getResultList();
        } catch(Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
