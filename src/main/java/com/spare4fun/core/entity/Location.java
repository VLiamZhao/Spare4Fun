package com.spare4fun.core.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Table(name = "location")

public class Location {
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
