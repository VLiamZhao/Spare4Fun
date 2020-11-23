package com.spare4fun.core.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/** Represents table of location.
 * @author Yuhe Gu
 * @author www.spare4fun.com
 * @version 1.0
 * @since 1.0
 */

@Entity
@Table(name = "location")

public class Location implements Serializable {

    private static final long serialVersionUID = -2455760938054L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    @Setter
    private int id;

    @Column(name = "line_one")
    @Getter
    @Setter
    private String line1;

    @Column(name = "line_two")
    @Getter
    @Setter
    private String line2;

    @Getter
    @Setter
    private String city;

    @Getter
    @Setter
    private String state;

    @Getter
    @Setter
    private String zipcode;

    @Getter
    @Setter
    private String country;




}
