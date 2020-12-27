package com.spare4fun.core.dao;

import com.spare4fun.core.entity.PaymentOrder;

import java.util.List;

public interface PaymentOrderDao {
    /**
     * save a new paymentOrder to paymentOrder table
     * TODO in service ? dao
     * TODO 0: check paymentOrder, offer, seller, buyer, item exist
     * TODO 1: check paymentOrder is confirmed
     * TODO 2: check current timestamp is within valid time for this appointment
     * TODO 3: check paymentOrder variable is valid
     * @param paymentOrder
     * @return paymentOrder saved
     */
    PaymentOrder savePaymentOrder(PaymentOrder paymentOrder);

    /**
     * get an paymentOrder with paymentOrderId from table
     * @param paymentOrderId
     * @return null iff the paymentOrder with paymentOrderId doesn't exist
     */
    PaymentOrder getPaymentOrderById(int paymentOrderId);

    /**
     * delete an paymentOrder with paymentOrderId from table
     * do nothing if the paymentOrder doesn't exist
     * @param paymentOrderId
     */
    void deletePaymentOrderById(int paymentOrderId);

    /**
     * get all related paymentOrders for this user as buyer
     * @param userId
     * @return list of paymentOrder for this user as buyer
     */
    List<PaymentOrder> getAllPaymentOrdersBuyer(int userId);

    /**
     * get all related paymentOrders for this item with itemId
     * and user with userId as seller
     * @param userId
     * @param itemId
     * @return list of paymentOrder for this item and user as seller
     */
    List<PaymentOrder> getAllPaymentOrdersSeller(int userId, int itemId);
}
