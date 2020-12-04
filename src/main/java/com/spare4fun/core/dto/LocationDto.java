package com.spare4fun.core.dto;

import lombok.*;

/**
 * A general format of location
 * @author Xinrong Zhao
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocationDto {
    @Getter
    @Setter
    private Integer id;

    @Getter
    @Setter
    private String line1;

    @Getter
    @Setter
    private String line2;

    @Getter
    @Setter
    private String city;

    @Getter
    @Setter
    private String state;

    @Getter
    @Setter
    private String zipcode;

    @Getter
    @Setter
    private String country;
}
