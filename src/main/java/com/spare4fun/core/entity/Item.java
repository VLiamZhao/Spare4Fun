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
@Table(name = "item")
public class Item implements Serializable {
    private static final long serialVersionUID = 0L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    @Setter
    private int itemId;

    @Getter
    @Setter
    private String title;

    @Getter
    @Setter
    private String description;

    //@ManyToOne
    // private User seller;

    //@ManyToOne
    // private Location location;

    @Getter
    @Setter
    private boolean hideLocation;


    @Getter
    @Setter
    private int quantity;


    @Getter
    @Setter
    private int quantityOnHold;


    @Getter
    @Setter
    private int quantitySold;


    @Getter
    @Setter
    private int listingPrice;


    @Getter
    @Setter
    private boolean fixedPrice;


    @Getter
    @Setter
    private String availabilityTime;


    @Getter
    @Setter
    private boolean enabled;


    //@OneToOne
    //private Category category;


    //@OneToOne
    //private ItemCondition condition;
}

