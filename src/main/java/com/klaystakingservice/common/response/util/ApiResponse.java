package com.klaystakingservice.common.response.util;

import com.klaystakingservice.common.response.dto.MessageDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ApiResponse {

    public static MessageDTO message(String msg){
        return MessageDTO.builder().message(msg).build();
    }

    public static ResponseEntity<MessageDTO> set(HttpStatus status, String responseUrl, String responseMessage){
        return ResponseEntity.status(status).header("Location", responseUrl).body(message(responseMessage));
    }
}
