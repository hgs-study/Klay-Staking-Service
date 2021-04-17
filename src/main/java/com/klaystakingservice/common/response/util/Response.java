package com.klaystakingservice.common.response.util;

import com.klaystakingservice.common.response.dto.MessageDTO;

public class Response {

    public static MessageDTO message(String msg){
        return MessageDTO.builder().message(msg).build();
    }
}
