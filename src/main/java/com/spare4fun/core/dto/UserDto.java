package com.spare4fun.core.dto;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    @Getter
    @Setter
    private String username;

    @Getter
    @Setter
    private String password;

}
