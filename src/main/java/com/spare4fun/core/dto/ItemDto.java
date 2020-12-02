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
    private Integer itemId;

    @Getter
    @Setter
    private String title;

    @Getter
    @Setter
    private String description;

    @Getter
    @Setter
    private Integer sellerId;

    @Getter
    @Setter
    private String sellerName;

    @Getter
    @Setter
    private Integer locationId;

    @Getter
    @Setter
    private Boolean hideLocation;

    @Getter
    @Setter
    private LocationDto locationDto;

    @Getter
    @Setter
    private Integer quantity;

    @Getter
    @Setter
    private Integer listingPrice;

    @Getter
    @Setter
    private Boolean fixedPrice;

    @Getter
    @Setter
    private String availabilityTime;

    @Getter
    @Setter
    private String category;

    @Getter
    @Setter
    private String condition;
}
