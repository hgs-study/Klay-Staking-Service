package com.klaystakingservice.common.response.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class MessageDTO {
    private String message;

    @Builder
    private MessageDTO(String message){
        this.message = message;
    }
}
