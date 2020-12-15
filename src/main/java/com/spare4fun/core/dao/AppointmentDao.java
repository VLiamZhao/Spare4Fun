package com.spare4fun.core.dao;

import com.spare4fun.core.entity.Appointment;
import com.spare4fun.core.entity.TimeSlot;

import java.util.List;

public interface AppointmentDao {
    /**
     * save a new appointment to appointment table
     * TODO 1: check no duplicate (user, item) pair
     * TODO 2: check no duplicate (appointment, offer) pair
     * @param appointment
     * @return appointment saved
     */
    Appointment saveAppointment(Appointment appointment);

    /**
     * get an appointment with appointmentId from table
     * @param appointmentId
     * @return null iff the appointment with appointmentId doesn't exist
     */
    Appointment getAppointmentById(int appointmentId);

    /**
     * delete an appointment with appointmentId from table
     * do nothing if the appointment doesn't exist
     * @param appointmentId
     */
    void deleteAppointmentById(int appointmentId);

    /**
     * get all related appointments for this user as buyer
     * @param userId
     * @param includeConfirmed
     * @param includeUnconfirmed
     * @return list of appointment for this user as buyer
     */
    List<Appointment> getAllAppointmentsBuyer(
            int userId, boolean includeConfirmed, boolean includeUnconfirmed);

    /**
     * get all related appointments for this item with itemId
     * and user with userId as seller
     * @param userId
     * @param itemId
     * @param includeConfirmed
     * @param includeUnconfirmed
     * @return list of appointment for this item and user as seller
     */
    List<Appointment> getAllAppointmentsSeller(
            int userId, int itemId, boolean includeConfirmed, boolean includeUnconfirmed);

    /**
     * confirm the meeting time of appointment with appointmentId
     * TODO 1: check the meeting time is 1 of availability time under appointment
     * TODO 2: check the current appointment is not confirmed yet
     * TODO 3: apponitment can only be confirmed by buyer for this appointment
     * @param appointmentId
     * @param timeSlotId
     * @param buyerId
     * @param timestamp - ISO 8601 format of time user made the confirm action
     * @return appointment that is confirmed
     * @throws
     *   InvalidAppointmentException
     *      - appointment doesn't exist
     *      - appointment is confirmed
     *      - appointment is canceled
     *
     *   InvalidTimeSlotException
     *      - timeSlot doesn't exist
     *      - timeSlot is not under this appointment
     *
     *   InvalidUserException
     *      - this buyer has no authorization to do the confirm this appointment
     *
     *   InvalidActionException
     *      - if the current timestamp already passed this timeslot
     */
    Appointment confirmAppointmentWithTimeSlot(
            int appointmentId, int timeSlotId, int buyerId, String timestamp);
}
