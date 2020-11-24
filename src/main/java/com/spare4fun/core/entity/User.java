package com.spare4fun.core.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/** Represents an user.
 * @author Xinrong Zhao
 * @author www.spare4fun.com
 * @version 1.0
 * @since 1.0
 */
@Entity
@Table(name="user")
public class User implements Serializable {
    private static final long serialVersionUID = 1617181920L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    @Setter
    private int id;

    @Getter
    @Setter
    private String email;

    @Getter
    @Setter
    private String password;

    @Getter
    @Setter
    private boolean enabled;

    @Getter
    @Setter
    @OneToOne(mappedBy = "user")
    private UserInfo userInfo;

    @Getter
    @Setter
    @OneToOne(mappedBy = "authorities")
    private Authorities authorities;

    @Getter
    @Setter
    @OneToMany(mappedBy = "seller")
    private List<Item> items;
}
