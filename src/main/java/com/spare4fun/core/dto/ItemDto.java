package com.spare4fun.core.dto;

import lombok.*;

/**
 * A general format of item
 * @author Jin Zhang
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemDto {
    @Getter
    @Setter
    private int itemId;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String title;

    @Getter
    @Setter
    private String description;

    @Getter
    @Setter
    private int categoryId;

    @Getter
    @Setter
    private int sellerId;

    @Getter
    @Setter
    private int locationId;

    @Getter
    @Setter
    private int listingPrice;

    @Getter
    @Setter
    private int fixedPrice;

    @Getter
    @Setter
    private int quantity;
}
