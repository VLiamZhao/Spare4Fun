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
    private Integer OfferId;
    private Integer price;
    private Integer quantity;
    private String message;
    private Integer itemId;
    private String sellerName;
    private Integer sellerId;
    private String buyerName;
    private Integer buyerId; 
}