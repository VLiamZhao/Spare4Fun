package com.spare4fun.core.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;

@Entity
@Table(name = "payment_order")
public class PaymentOrder implements Serializable {
    private static final long serialVersionUID = 1617181920L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    @Setter
    private int id;

    @OneToOne
    // unidrectional mapping
    @JoinColumn(name="appointment_id", nullable=false)
    @Getter
    @Setter
    private Appointment appointment;

    @OneToOne
    // unidirectional mapping
    @JoinColumn(name="offer_id", nullable=false)
    @Getter
    @Setter
    private Offer offer;

    @ManyToOne
    @JoinColumn(name="item_id", nullable=false)
    @Getter
    @Setter
    private Item item;

    @ManyToOne
    @JoinColumn(name="seller_id", nullable=false)
    @Getter
    @Setter
    private User seller;

    @ManyToOne
    @JoinColumn(name="buyer_id", nullable=false)
    @Getter
    @Setter
    private User buyer;

    @Getter
    @Setter
    @Column(name = "init_time")
    private String initTime;

    @Getter
    @Setter
    @Column(name = "expire_time")
    private String expireTime;

    @Getter
    @Setter
    @Column(name = "final_price")
    @Min(0.01)
    private float finalPrice;

    @Getter
    @Setter
    @Column(name = "final_quantity")
    @Min(1)
    private int finalQuantity;

    @Getter
    @Setter
    private boolean completed;
}
