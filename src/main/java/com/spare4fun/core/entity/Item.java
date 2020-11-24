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
    private int itemid;

    @Getter
    @Setter
    private String title;

    @Getter
    @Setter
    private String description;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "seller_id", referencedColumnName = "id")
    private User seller;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private Location location;

    @Getter
    @Setter
    private boolean hide_location;


    @Getter
    @Setter
    private int quantity;


    @Getter
    @Setter
    private int quantity_on_hold;


    @Getter
    @Setter
    private int quantity_sold;


    @Getter
    @Setter
    private int listing_price;


    @Getter
    @Setter
    private boolean fixed_price;


    @Getter
    @Setter
    private String availability_time;


    @Getter
    @Setter
    private boolean enabled;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "condition_id", referencedColumnName = "id")
    private ItemCondition condition;
}

