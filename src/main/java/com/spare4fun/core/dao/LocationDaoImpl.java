package com.spare4fun.core.dao;

import com.spare4fun.core.entity.Location;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class LocationDaoImpl implements LocationDao {
        @Autowired
        private SessionFactory sessionFactory;
        //Please ignore this method. This is only for temporary test
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

        //Please ignore this method. This is only for temporary test
        public Location deleteLocationById(int locationId) {
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
            return null;
        }
}
