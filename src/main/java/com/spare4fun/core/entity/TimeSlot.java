package com.spare4fun.core.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/** Represents time slot with start time and end time.
 * @see <a href="https://docs.google.com/document/d/1qWrp03iASz7vVdmsn8NqVpqJNkYVVl7UkUPhOUgOHDs/edit?usp=sharing">Database Doc</a>
 * @author Xinrong Zhao
 * @author www.spare4fun.com
 * @version 1.0
 * @since 1.0
 */
@Entity
@Table(name = "time_slot")
public class TimeSlot implements Serializable {
    private static final long serialVersionUID = 1112131415L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    @Setter
    private int id;

    @ManyToOne
    @JoinColumn(name="appointment_id", nullable=false)
    // Define appointment as foreign key for appointment_id
    @Getter
    @Setter
    private Appointment appointment;

    @Getter
    @Setter
    private String startTime;

    @Getter
    @Setter
    private String endTime;
}
