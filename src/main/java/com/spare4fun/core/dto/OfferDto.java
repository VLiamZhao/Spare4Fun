package com.spare4fun.core.dto;

import lombok.Data;

@Data
public class OfferDto {
    private int price;
    private int quantity;
    private String message;
    private int itemId;
    private int buyerId;
}