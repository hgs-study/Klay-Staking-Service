package com.klaystakingservice.common.response.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ResponseDTO {

    @ApiModelProperty(example = "상태코드")
    private int status;

    @ApiModelProperty(example = "메세지")
    private String message;

    @ApiModelProperty(example = "응답데이터")
    private String data;

    @ApiModelProperty(example = "시간")
    private LocalDateTime timestamp;


    @Builder
    public ResponseDTO(HttpStatus httpStatus,String data){
        this.status = httpStatus.value();
        this.message = httpStatus.getReasonPhrase();
        this.data = data;
        this.timestamp = LocalDateTime.now();
    }
}
