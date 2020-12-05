package com.spare4fun.core.dao;

import com.spare4fun.core.entity.TimeSlot;
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
public class TimeSlotDaoImpl implements TimeSlotDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public TimeSlot saveTimeSlot(TimeSlot timeSlot) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.save(timeSlot);
            session.getTransaction().commit();
            return timeSlot;
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
    public TimeSlot getTimeSlotById(int timeSlotId) {
        TimeSlot timeSlot = null;
        try (Session session = sessionFactory.openSession()) {
            timeSlot = session.get(TimeSlot.class, timeSlotId);
        }catch (NoResultException e) {
            return null;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return timeSlot;
    }

    @Override
    public void deleteTimeSlotById(int timeSlotId) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            TimeSlot timeSlot = session.get(TimeSlot.class, timeSlotId);
            session.beginTransaction();
            session.delete(timeSlot);
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
    public List<TimeSlot> getAllTimeSlot(int appointmentId) {
        List<TimeSlot> timeSlots = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<TimeSlot> criteriaQuery = builder.createQuery(TimeSlot.class);
            Root<TimeSlot> root = criteriaQuery.from(TimeSlot.class);
            criteriaQuery
                    .select(root)
                    .where(builder.equal(root.get("appointment"), appointmentId));
            timeSlots = session
                    .createQuery(criteriaQuery)
                    .getResultList();
            session.getTransaction().commit();
        } catch (NoResultException e) {
            return Collections.emptyList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return timeSlots;
    }
}
