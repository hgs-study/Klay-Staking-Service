package com.klaystakingservice.common.response.util;

import com.klaystakingservice.common.response.dto.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ApiResponse {


    public static ResponseEntity<ResponseDTO> set(HttpStatus httpStatus, String responseUrl, String responseMessage){
        return ResponseEntity.status(httpStatus)
                             .header("Location", responseUrl)
                             .body(new ResponseDTO(httpStatus, responseMessage));
    }
}
