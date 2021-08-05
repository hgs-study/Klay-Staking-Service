package com.klaystakingservice.common.security.handler;

import com.klaystakingservice.common.redis.RedisUtil;
import com.klaystakingservice.common.security.cookie.CookieUtil;
import com.klaystakingservice.common.security.jwt.JwtProperties;
import com.klaystakingservice.common.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;

@Component
@Slf4j
@RequiredArgsConstructor
public class CustomLogoutHandler implements LogoutHandler {

    private final CookieUtil cookieUtil;
    private final RedisUtil redisUtil;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        log.debug("===== CustomLogoutHandler start =====");
        final Cookie userRefreshTokenCookie = cookieUtil.getCookie(request, JwtProperties.REFRESH_TOKEN_NAME);
        final String userRefreshToken  = userRefreshTokenCookie.getValue();

        final String emptyAccessToken = jwtTokenProvider.createToken("", Collections.singletonList(""), JwtProperties.EXPIRE_TOKEN_EXPIRATION_TIME);
        final String emptyRefreshToken = jwtTokenProvider.createToken("", Collections.singletonList(""), JwtProperties.EXPIRE_TOKEN_EXPIRATION_TIME);

        final Cookie accessTokenCookie = cookieUtil.createCookie(JwtProperties.ACCESS_TOKEN_NAME, emptyAccessToken, JwtProperties.ACCESS_TOKEN_EXPIRATION_TIME);
        final Cookie refreshTokenCookie = cookieUtil.createCookie(JwtProperties.REFRESH_TOKEN_NAME, emptyRefreshToken, JwtProperties.REFRESH_TOKEN_EXPIRATION_TIME);

        redisUtil.setDataExpire(userRefreshToken, "expire", JwtProperties.EXPIRE_TOKEN_EXPIRATION_TIME);

        response.addCookie(accessTokenCookie);
        response.addCookie(refreshTokenCookie);

    }
}
