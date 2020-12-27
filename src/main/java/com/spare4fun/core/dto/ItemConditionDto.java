package com.spare4fun.core.dto;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemConditionDto {
    @Getter
    @Setter
    private Integer itemConditionId;

    @Getter
    @Setter
    private String label;
}
