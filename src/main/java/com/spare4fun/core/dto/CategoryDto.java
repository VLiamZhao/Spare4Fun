package com.spare4fun.core.dto;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {
    @Getter
    @Setter
    private Integer categoryId;

    @Getter
    @Setter
    private String category;
}
