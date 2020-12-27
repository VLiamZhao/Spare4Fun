package com.spare4fun.core.dao;

import com.spare4fun.core.entity.Appointment;
import com.spare4fun.core.entity.TimeSlot;
import com.spare4fun.core.exception.InvalidActionException;
import com.spare4fun.core.exception.InvalidAppointmentException;
import com.spare4fun.core.exception.InvalidTimeSlotException;
import com.spare4fun.core.exception.InvalidUserException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
public class AppointmentDaoImpl implements AppointmentDao {
    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private TimeSlotDao timeSlotDao;

    @Override
    public Appointment saveAppointment(Appointment appointment) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.save(appointment);
            session.getTransaction().commit();
            return appointment;
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
    public Appointment getAppointmentById(int appointmentId) {
        Appointment appointment = null;
        try (Session session = sessionFactory.openSession()) {
            appointment = session.get(Appointment.class, appointmentId);
        }catch (NoResultException e) {
            return null;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return appointment;
    }

    @Override
    public void deleteAppointmentById(int appointmentId) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            Appointment appointment = session.get(Appointment.class, appointmentId);
            session.beginTransaction();
            session.delete(appointment);
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
    public List<Appointment> getAllAppointmentsBuyer(int userId, boolean includeConfirmed, boolean includeUnconfirmed) {
        // sanity check: neither include confirmed nor unconfirmed
        if (!includeConfirmed && !includeUnconfirmed) return new ArrayList<>();

        List<Appointment> appointments = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Appointment> criteriaQuery = builder.createQuery(Appointment.class);
            Root<Appointment> root = criteriaQuery.from(Appointment.class);
            Predicate predicate = builder.and(builder.equal(root.get("buyer"), userId));
            if (!includeConfirmed || !includeUnconfirmed) {
                // only Confirmed or only Unconfirmed
                predicate = builder.and(predicate,
                        builder.equal(root.get("confirmed"), includeConfirmed));
            }

            criteriaQuery
                    .select(root)
                    .where(predicate);
            appointments = session
                    .createQuery(criteriaQuery)
                    .getResultList();
            session.getTransaction().commit();
        } catch (NoResultException e) {
            return Collections.emptyList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return appointments;
    }

    @Override
    public List<Appointment> getAllAppointmentsSeller(int userId, int itemId, boolean includeConfirmed, boolean includeUnconfirmed) {
        // sanity check: neither include confirmed nor unconfirmed
        if (!includeConfirmed && !includeUnconfirmed) return new ArrayList<>();

        List<Appointment> appointments = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Appointment> criteriaQuery = builder.createQuery(Appointment.class);
            Root<Appointment> root = criteriaQuery.from(Appointment.class);
            Predicate predicate = builder.and(
                    builder.equal(root.get("seller"), userId),
                    builder.equal(root.get("item"), itemId));
            if (!includeConfirmed || !includeUnconfirmed) {
                // only Confirmed or only Unconfirmed
                predicate = builder.and(predicate,
                        builder.equal(root.get("confirmed"), includeConfirmed));
            }

            criteriaQuery
                    .select(root)
                    .where(predicate);
            appointments = session
                    .createQuery(criteriaQuery)
                    .getResultList();
            session.getTransaction().commit();
        } catch (NoResultException e) {
            return Collections.emptyList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return appointments;
    }

    @Override
    public Appointment confirmAppointmentWithTimeSlot(
            int appointmentId, int timeSlotId, int buyerId, String timestamp) {
        Appointment appointment = getAppointmentById(appointmentId);
        if (appointment == null) {
            throw new InvalidAppointmentException(
                    String.format("Appointment %d doesn't exist", appointmentId)
            );
        }

        if (appointment.getBuyer().getId() != buyerId) {
            throw new InvalidUserException(
                    String.format("User %d isn't authorized to do confirmation of appointment %d",
                            buyerId, appointmentId)
            );
        }

        if (appointment.isConfirmed()) {
            throw new InvalidAppointmentException(
                    String.format("Appointment %d is already confirmed", appointmentId)
            );
        }

        TimeSlot timeSlot = timeSlotDao.getTimeSlotById(timeSlotId);
        if (timeSlot == null) {
            throw new InvalidTimeSlotException(
                    String.format("Time slot %d doesn't exist", timeSlotId)
            );
        }

        if (timeSlot.getAppointment().getId() != appointmentId) {
            throw new InvalidTimeSlotException(
                    String.format("Time slot %d is not under appointment %d", timeSlotId, appointmentId)
            );
        }

        Instant start = Instant.parse(timeSlot.getStartTime());
        Instant current = Instant.parse(timestamp);
        if (start.compareTo(current) < 0) {
            // start is earlier than current
            throw new InvalidActionException(
                    String.format("Timestamp %s is invalid, cannot complete the action", timestamp)
            );
        }

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            appointment.setConfirmedTime(timeSlot);
            appointment.setConfirmed(true);
            session.saveOrUpdate(appointment);
            session.getTransaction().commit();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return appointment;
    }
}
