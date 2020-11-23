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
    private int id;

    //@OneToOne(cascade = CascadeType.ALL)
    //private Offer offer;

    //@OneToOne(cascade = CascadeType.ALL)
    //private Item item;

    // @ManyToOne(cascade = CascadeType.ALL)
    // private User seller;

    //@ManyToOne(cascade = CascadeType.ALL)
    //private User buyer;

   // @ManyToOne(cascade = CascadeType.ALL)
   // private Location location;

    //@ManyToOne(cascade = CascadeType.ALL)
    //private TimeSlot timeSlot;

    @Getter
    @Setter
    private boolean confirmed;

    @Getter
    @Setter
    private boolean canceled;

    //@OneToMany(cascade = CascadeType.ALL)
    //private List<TimeSlot> availTime;
}
