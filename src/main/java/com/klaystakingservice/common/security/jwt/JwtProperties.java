package com.klaystakingservice.common.security.jwt;

public class JwtProperties {
    public static final String SECRET_KEY = "hgstudy";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String REQUEST_HEADER_NAME = "Authorization";
    public static final String RESPONSE_HEADER_NAME = "Token";
    public static final String ACCESS_TOKEN_NAME = "Access-Token";
    public static final String REFRESH_TOKEN_NAME = "Refresh-Token";
    public static final long ACCESS_TOKEN_EXPIRATION_TIME = 30 * 60 * 1000L; // 30분
    public static final long REFRESH_TOKEN_EXPIRATION_TIME = 2 * 24 * 60 * 60 * 1000L; // 2일
    public static final long EXPIRE_TOKEN_EXPIRATION_TIME = 1000L; // 2일
}

