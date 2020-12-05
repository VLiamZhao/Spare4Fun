package com.spare4fun.core.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OfferDto {
    private int price;
    private int quantity;
    private String message;
    private int itemId;
    private String sellerName;
}