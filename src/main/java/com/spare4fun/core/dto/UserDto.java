package com.spare4fun.core.dto;

import lombok.*;

/**
 * A general format of user
 * @author Xinrong Zhao
 */

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
