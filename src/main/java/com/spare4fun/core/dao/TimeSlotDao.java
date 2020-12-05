package com.spare4fun.core.dao;

import com.spare4fun.core.entity.TimeSlot;

import java.util.List;

public interface TimeSlotDao {
    /**
     * save a new timeSlot to time slot table
     * TODO 1: make sure start < end
     * @param timeSlot
     * @return the time slot saved
     */
    TimeSlot saveTimeSlot(TimeSlot timeSlot);

    /**
     * get a timeSlot by timeSlotId
     * @param timeSlotId
     * @return
     */
    TimeSlot getTimeSlotById(int timeSlotId);

    /**
     * delete the timeSlot with timeSlotId
     * do nothing if this timeSlot doesn't exist
     * @param timeSlotId
     */
    void deleteTimeSlotById(int timeSlotId);

    /**
     * @param appointmentId
     * @return a list of time slot binded with the appointment
     *    return empty list iff appointment doesn't exist
     */
    List<TimeSlot> getAllTimeSlot(int appointmentId);
}
