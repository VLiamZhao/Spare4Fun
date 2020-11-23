package com.spare4fun.core.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.xml.stream.Location;
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
    private int id;

    //@OneToOne
    //private Offer id;

    //@OneToOne
    //private Item id;

    //@ManyToOne
    //private User sellerId;

    //@ManyToOne
    //private User buyerId;

   // @ManyToOne
   // private Location location;

    //@ManyToOne
    //private TimeSlot timeSlot;

    @Getter
    @Setter
    private boolean confirmed;

    @Getter
    @Setter
    private boolean canceled;
}
