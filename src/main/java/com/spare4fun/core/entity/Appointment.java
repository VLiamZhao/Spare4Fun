package com.spare4fun.core.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/** Represents an user.
 * @author Jin Zhang
 * @author www.spare4fun.com
 * @version 1.0
 * @since 1.0
 */
@Entity
@Table(name = "appointment")
public class Appointment implements Serializable {
    private static final long serialVersionUID = 0L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    @Setter
    private int appointmentId;

    @Getter
    @Setter
    private int offerId;

    @Getter
    @Setter
    private int itemId;

    //@ManyToOne
    //private User sellerId;

    //@ManyToOne
    //private User buyerId;

    @Getter
    @Setter
    private boolean location;

    @Getter
    @Setter
    private int timeSlot;

    @Getter
    @Setter
    private boolean confirmed;

    @Getter
    @Setter
    private boolean canceled;
}
