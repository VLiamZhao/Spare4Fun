package com.spare4fun.core.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

/** Represents an user.
 * @author Jin Zhang
 * @author www.spare4fun.com
 * @version 1.0
 * @since 1.0
 */
@Entity
@Table(name = "appointment")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Appointment implements Serializable {
    private static final long serialVersionUID = 0L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    @Setter
    private int id;

    @Getter
    @Setter
    @OneToOne
    @JoinColumn(name = "offer_id", referencedColumnName = "id", nullable = false)
    private Offer offer;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "item_id", referencedColumnName = "id", nullable = false)
    private Item item;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "seller_id", referencedColumnName = "id", nullable = false)
    private User seller;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "buyer_id", referencedColumnName = "id", nullable = false)
    private User buyer;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "location_id", referencedColumnName = "id", nullable = false)
    private Location location;

    @Getter
    @Setter
    private boolean confirmed;

    @Getter
    @Setter
    @OneToOne
    @JoinColumn(name = "confirmed_time_id", referencedColumnName = "id")
    private TimeSlot confirmedTime;

    @Getter
    @Setter
    @OneToMany(mappedBy = "appointment", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Column(name = "avail_time")
    private List<TimeSlot> availTime;

    @Getter
    @Setter
    @OneToOne(mappedBy = "appointment")
    private PaymentOrder paymentOrder;
}
