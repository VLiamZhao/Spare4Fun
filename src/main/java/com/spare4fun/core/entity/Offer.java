package com.spare4fun.core.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/** Represents table of location.
 * @author Yuhe Gu
 * @author www.spare4fun.com
 * @version 1.0
 * @since 1.0
 */

@Entity
@Table(name = "offer")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Offer implements Serializable {

    private static final long serialVersionUID = -24557609380542533L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    @Setter
    private int id;

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
    private int price;

    @Getter
    @Setter
    private int quantity;

    @Getter
    @Setter
    private String message;

    @Getter
    @Setter
    @OneToOne(mappedBy = "offer")
    private Appointment appointment;

    @Getter
    @Setter
    @OneToOne(mappedBy = "offer")
    private PaymentOrder paymentOrder;
}
