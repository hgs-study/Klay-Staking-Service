package com.klaystakingservice.common.error.code;

import lombok.Getter;

@Getter
public enum  ErrorCode {

    //COMMON
    INVALID_REQUEST(400,"C004001","잘못된 요청입니다."),
    RESOURCE_NOT_FOUND(404,"COO4002","해당 리소스를 찾을 수 없습니다."),
    FORBIDDEN(403,"C004003","해당 권한이 없습니다."),
    SERVER_ERROR(500,"COO5001","서버에서의 오류입니다."),

    //LOGIN
    EMAIL_NOT_FOUND(400,"L004001","해당 이메일 주소를 찾을 수 없습니다."),

    //TOKEN
    TOKEN_NOT_FOUND(400,"T004001","해당 토큰을 찾을 수 없습니다."),

    //WALLET
    WALLET_NOT_FOUND(400,"W004001","해당 지갑을 찾을 수 없습니다.");

    private int status;
    private String code;
    private String message;

    ErrorCode(int status, String code, String message){
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
