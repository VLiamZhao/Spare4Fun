package com.spare4fun.core.entity;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/** Represents an user.
 * @author Xinrong Zhao
 * @author www.spare4fun.com
 * @version 1.0
 * @since 1.0
 */

@Entity
@Table(name="user")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails, Serializable {
    private static final long serialVersionUID = 23526467L;

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
    @OneToMany(mappedBy = "seller")
    private List<Item> items;

    @Getter
    @Setter
    @OneToOne(mappedBy = "user")
    private SavedItemsCart savedItemsCart;

    @Getter
    @Setter
    @OneToMany(mappedBy = "seller")
    private List<Offer> sellerOffer;

    @Getter
    @Setter
    @OneToMany(mappedBy = "buyer")
    private List<Offer> buyerOffer;

    @Getter
    @Setter
    @OneToMany(mappedBy = "seller")
    private List<Appointment> sellerAppointment;

    @Getter
    @Setter
    @OneToMany(mappedBy = "buyer")
    private List<Appointment> buyerAppointment;

    @Getter
    @Setter
    @OneToMany(mappedBy = "seller")
    private List<PaymentOrder> sellerPaymentOrder;

    @Getter
    @Setter
    @OneToMany(mappedBy = "buyer")
    private List<PaymentOrder> buyerPaymentOrder;

    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    private Role role;

    @Transient
    private final boolean accountNonExpired = true;

    @Transient
    private final boolean accountNonLocked = true;

    @Transient
    private final boolean credentialsNonExpired = true;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getGrantedAuthorities();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
