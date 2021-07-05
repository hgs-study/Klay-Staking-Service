package com.klaystakingservice.common.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.klaystakingservice.business.account.application.AccountService;
import com.klaystakingservice.business.account.entity.Account;
import com.klaystakingservice.business.account.form.AccountForm;
import com.klaystakingservice.common.error.code.ErrorCode;
import com.klaystakingservice.common.error.exception.BusinessException;
import com.klaystakingservice.common.redis.RedisUtil;
import com.klaystakingservice.common.security.cookie.CookieUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final AccountService accountService;
    private final RedisUtil redisUtil;
    private final CookieUtil cookieUtil;

    private final String ACCESS_TOKEN_NAME = JwtProperties.ACCESS_TOKEN_NAME;
    private final String REFRESH_TOKEN_NAME = JwtProperties.REFRESH_TOKEN_NAME;
    private final long REFRESH_TOKEN_EXPIRATION_TIME = JwtProperties.REFRESH_TOKEN_EXPIRATION_TIME;
    private final long ACCESS_TOKEN_EXPIRATION_TIME = JwtProperties.ACCESS_TOKEN_EXPIRATION_TIME;

    @Override
    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        try {
            log.debug("==== attemptAuthentication start ====");
            //"/login"시 1번째로 탐 / getInputStream() : post 형태로 오는 것을 받을 수 있음
            AccountForm.Request.Login creds = new ObjectMapper().readValue(request.getInputStream(), AccountForm.Request.Login.class);


            //UsernamePasswordAuthenticationToken 토큰 생성 후 AuthenticationManager에 인증작업 요청
            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.getEmail(),
                            creds.getPassword(),
                            new ArrayList<>()
                    )
            );
        }
        catch (IOException e){
            throw new BusinessException(ErrorCode.EMAIL_NOT_FOUND);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        log.debug("==== successfulAuthentication start ====");

        String email = ((Account)authResult.getPrincipal()).getEmail();
        Account member = accountService.findByEmail(email);

        final String accessToken = jwtTokenProvider.createToken(member.getUserKey(), member.getRoles(), ACCESS_TOKEN_EXPIRATION_TIME);
        final String refreshToken = jwtTokenProvider.createToken(member.getUserKey(), member.getRoles(), REFRESH_TOKEN_EXPIRATION_TIME);

        final Cookie accessTokenCookie = cookieUtil.createCookie(ACCESS_TOKEN_NAME, accessToken, ACCESS_TOKEN_EXPIRATION_TIME);
        final Cookie refreshTokenCookie = cookieUtil.createCookie(REFRESH_TOKEN_NAME, refreshToken, REFRESH_TOKEN_EXPIRATION_TIME);

        redisUtil.setDataExpire(refreshToken, member.getUserKey() , JwtProperties.REFRESH_TOKEN_EXPIRATION_TIME);

        response.addCookie(accessTokenCookie);
        response.addCookie(refreshTokenCookie);

        response.addHeader(JwtProperties.RESPONSE_HEADER_NAME,accessToken);
        response.addHeader("userId", member.getUserKey());
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {
        logger.debug("failed authentication while attempting to access ");

        //Add more descriptive message
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, failed.getMessage());
    }
}
