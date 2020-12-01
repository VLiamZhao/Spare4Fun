package com.spare4fun.core.dto;

import lombok.*;

/**
 * A general format of message
 * @author Xinrong Zhao
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageDto {
    public enum Status {
        FAILURE,
        SUCCESS
    }

    @Getter
    @Setter
    private Status status;

    @Getter
    @Setter
    private String message;
}
