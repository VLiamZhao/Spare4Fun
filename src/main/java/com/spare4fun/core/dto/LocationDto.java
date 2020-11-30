package com.spare4fun.core.dto;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocationDto {
    // TODO: if hideLocation
    //         only give zip code


    // same as location
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
